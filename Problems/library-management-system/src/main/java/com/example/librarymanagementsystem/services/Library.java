package com.example.librarymanagementsystem.services;

import com.example.librarymanagementsystem.constants.Constant;
import com.example.librarymanagementsystem.models.Book;
import com.example.librarymanagementsystem.models.BookCopy;
import com.example.librarymanagementsystem.models.User;
import com.example.librarymanagementsystem.services.search.*;
import com.example.librarymanagementsystem.utils.Util;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Library {
    private int numberOfRacks;
    private int bookBorrowLimit = 5;
    private List<Book> books;
    private List<BookCopy> bookCopies;
    private List<User> users;
    private final Lock libraryLock;
    private static volatile Library libraryInstance;

    SearchStrategy bookIDSearchStrategy, titleSearchStrategy, authorSearchStrategy, publisherSearchStrategy;

    private Library() {
        this.books = new ArrayList<>();
        this.bookCopies = new ArrayList<>();
        this.users = new ArrayList<>();

        libraryLock = new ReentrantLock();

        bookIDSearchStrategy = new BookIDSearchStrategy();
        titleSearchStrategy = new TitleSearchStrategy();
        authorSearchStrategy = new AuthorSearchStrategy();
        publisherSearchStrategy = new PublisherSearchStrategy();
    }

    public static Library getLibraryInstance() {
        if (libraryInstance == null) {
            synchronized (Library.class) {
                if (libraryInstance == null) {
                    libraryInstance = new Library();
                }
            }
        }

        return libraryInstance;
    }

    public void setNumberOfRacks(int numberOfRacks) {
        this.numberOfRacks = numberOfRacks;
        System.out.printf("Created library with %d racks\n", numberOfRacks);
    }

    private void lockLibrary() {
        libraryLock.lock();
    }

    private void unlockLibrary() {
        libraryLock.unlock();
    }

    public void setBookBorrowLimit(int bookBorrowLimit) {
        this.bookBorrowLimit = bookBorrowLimit;
    }

    public void addBook(int bookID, String title, List<String> authors, List<String> publishers, List<String> bookCopyIDs) {
        lockLibrary();

        try {
            int totalRacksAvailable = numberOfRacks - bookCopies.size();
            if(totalRacksAvailable < bookCopyIDs.size()) {
                System.out.println("Rack not available");
                return;
            }

            Book book = new Book(bookID, title, authors, publishers, bookCopyIDs);
            books.add(book);

            List<Integer> unavailableRackNumbers = new ArrayList<>(bookCopies.stream().map(BookCopy::getRackNumber).toList());

            List<Integer> availableRackNumbers = new ArrayList<>();
            for(int i = 1; i <= numberOfRacks; i++) {
                availableRackNumbers.add(i);
            }

            availableRackNumbers.removeAll(unavailableRackNumbers);

            List<Integer> rackNumbersUsed = new ArrayList<>();

            for(String bookCopyID: bookCopyIDs) {
                if(bookCopies.stream().noneMatch(bookCopy -> bookCopy.getBookCopyID().equals(bookCopyID))) {
                    int rackNumber = availableRackNumbers.remove(0);
                    BookCopy bookCopy = new BookCopy(bookCopyID, bookID, rackNumber, "", Util.generateDefaultDate());
                    bookCopies.add(bookCopy);
                    rackNumbersUsed.add(rackNumber);
                }
            }

            System.out.print("Added Book to racks: ");
            for(int i = 0; i < rackNumbersUsed.size(); i++) {
                System.out.print(rackNumbersUsed.get(i));

                if(i != (rackNumbersUsed.size() - 1)) {
                    System.out.print(",");
                } else {
                    System.out.println();
                }
            }

        } finally {
            unlockLibrary();
        }
    }

    public void removeBookCopy(String bookCopyID) {
        libraryLock.lock();

        try {
            Optional<BookCopy> optionalBookCopy = bookCopies.stream().filter(bookCopy -> bookCopy.getBookCopyID().equals(bookCopyID)).findFirst();

            if(optionalBookCopy.isPresent()) {
                BookCopy bookCopy = optionalBookCopy.get();

                if(bookCopy.getRackNumber() == Constant.RACK_FOR_BORROWED_BOOK_COPY) {
                    System.out.printf("Cannot remove borrowed book copy: %s\n", bookCopyID);
                    return;
                }

                int bookID = bookCopy.getBookID();
                for(Book value: books) {
                    if(value.getBookID() == bookID) {
                        value.removeBookCopyID(bookCopyID);
                    }
                }

                bookCopies.remove(bookCopy);
                System.out.printf("Removed book copy: %s from rack: %d\n", bookCopyID, bookCopy.getRackNumber());
            } else {
                System.out.println("Invalid Book Copy ID");
            }
        } finally {
            libraryLock.unlock();
        }
    }

    public void borrowBook(int bookID, String userID, LocalDate dueDate) {
        libraryLock.lock();

        try {
            boolean bookIDFound = false;

            for(Book book: books) {
                if(book.getBookID() == bookID) {
                    bookIDFound = true;
                    break;
                }
            }

            if(!bookIDFound) {
                System.out.println("Invalid Book ID");
                return;
            }

            List<BookCopy> availableBookCopiesForBorrow = new ArrayList<>();
            for(BookCopy bookCopy: bookCopies) {
                if((bookCopy.getBookID() == bookID) && (bookCopy.getRackNumber() != Constant.RACK_FOR_BORROWED_BOOK_COPY)) {
                    availableBookCopiesForBorrow.add(bookCopy);
                }
            }

            if(availableBookCopiesForBorrow.isEmpty()) {
                System.out.println("Not available");
                return;
            }

            User user;
            Optional<User> optionalUser = users.stream().filter(user1 -> user1.getUserID().equals(userID)).findFirst();

            if(optionalUser.isPresent()) {
                user = optionalUser.get();
            } else {
                user = new User(userID);
                users.add(user);
            }

            if(user.countOfBorrowedBookCopyIDs() == bookBorrowLimit) {
                System.out.println("Over limit");
                return;
            }

            availableBookCopiesForBorrow.sort(Comparator.comparingInt(BookCopy::getBookID));
            BookCopy bookCopyToBorrow = availableBookCopiesForBorrow.get(0);

            int borrowedBookRackNumber = bookCopyToBorrow.getRackNumber();
            bookCopyToBorrow.setBorrowedBy(userID);
            bookCopyToBorrow.setDueDate(dueDate);
            bookCopyToBorrow.setRackNumber(-1);

            user.addBorrowedBookCopyID(bookCopyToBorrow.getBookCopyID());
            System.out.printf("Borrowed Book from rack: %d", borrowedBookRackNumber);
        } finally {
            libraryLock.unlock();
        }
    }

    public void borrowBookCopy(String bookCopyID, String userID, LocalDate dueDate) {
        libraryLock.lock();

        try {
            BookCopy bookCopyToBorrow = null;

            for(BookCopy bookCopy: bookCopies) {
                if(bookCopy.getBookCopyID().equals(bookCopyID)) {
                    bookCopyToBorrow = bookCopy;
                    break;
                }
            }

            if(bookCopyToBorrow == null) {
                System.out.println("Invalid Book Copy ID");
                return;
            }

            User user;
            Optional<User> optionalUser = users.stream().filter(user1 -> user1.getUserID().equals(userID)).findFirst();

            if(optionalUser.isPresent()) {
                user = optionalUser.get();
            } else {
                user = new User(userID);
            }

            if(user.countOfBorrowedBookCopyIDs() == bookBorrowLimit) {
                System.out.println("Over limit");
                return;
            }

            if(bookCopyToBorrow.getRackNumber() == Constant.RACK_FOR_BORROWED_BOOK_COPY) {
                System.out.printf("Book Copy ID: %s, already assigned to: %s\n", bookCopyID, bookCopyToBorrow.getBorrowedBy());
                return;
            }

            int borrowedBookCopyRackNumber = bookCopyToBorrow.getRackNumber();
            bookCopyToBorrow.setBorrowedBy(userID);
            bookCopyToBorrow.setDueDate(dueDate);
            bookCopyToBorrow.setRackNumber(-1);

            user.addBorrowedBookCopyID(bookCopyToBorrow.getBookCopyID());
            System.out.printf("Borrowed Book Copy from rack: %d\n", borrowedBookCopyRackNumber);
        } finally {
            libraryLock.unlock();
        }
    }

    public void returnBookCopy(String bookCopyID) {
        libraryLock.lock();

        try {
            BookCopy bookCopyToReturn = null;

            for(BookCopy bookCopy: bookCopies) {
                if(bookCopy.getBookCopyID().equals(bookCopyID)) {
                    bookCopyToReturn = bookCopy;
                    break;
                }
            }

            if(bookCopyToReturn == null) {
                System.out.println("Invalid Book Copy ID");
                return;
            }

            String bookReturnUserID = bookCopyToReturn.getBorrowedBy();

            Optional<User> optionalUser = users.stream().filter(user -> user.getUserID().equals(bookReturnUserID)).findFirst();
            if(optionalUser.isPresent()) {
                User bookReturnUser = optionalUser.get();
                bookReturnUser.removeBorrowedBookCopyID(bookCopyID);

                bookCopyToReturn.setBorrowedBy("");
                bookCopyToReturn.setDueDate(Util.generateDefaultDate());

                List<Integer> unavailableRackNumbers = new ArrayList<>(bookCopies.stream().map(BookCopy::getRackNumber).toList());

                List<Integer> availableRackNumbers = new ArrayList<>();
                for(int i = 1; i <= numberOfRacks; i++) {
                    availableRackNumbers.add(i);
                }

                availableRackNumbers.removeAll(unavailableRackNumbers);

                int rackNumber = availableRackNumbers.remove(0);
                bookCopyToReturn.setRackNumber(rackNumber);

                System.out.printf("Returned book copy %s and added to rack: %d\n", bookCopyID, rackNumber);
            } else {
                throw new IllegalArgumentException(String.format("%s user ID not found", bookReturnUserID));
            }
        } finally {
            libraryLock.unlock();
        }
    }

    public void printBorrowed(String userID) {
        libraryLock.lock();

        try {
            User user = null;
            for(User value : users) {
                if(value.getUserID().equals(userID)) {
                    user = value;
                    break;
                }
            }

            if(user == null) {
                System.out.printf("User ID: %s not found\n", userID);
                return;
            }

            List<String> borrowedBookCopyIDs = user.getBorrowedBookCopyIDs();

            List<BookCopy> borrowedBookCopies = new ArrayList<>(bookCopies.stream()
                    .filter(bookCopy -> borrowedBookCopyIDs.contains(bookCopy.getBookCopyID()))
                    .toList());

            borrowedBookCopies.sort(Comparator.comparing(BookCopy::getBookCopyID));

            for(BookCopy value: borrowedBookCopies) {
                System.out.printf("Book Copy: %s %tF\n", value.getBookCopyID(), value.getDueDate());
            }
        } finally {
            libraryLock.unlock();
        }
    }

    public void search(String attribute, String value) {
        libraryLock.lock();

        try {
            switch(attribute) {
                case "book_id":
                    bookIDSearchStrategy.search(value, books, bookCopies);
                    break;
                case "title":
                    titleSearchStrategy.search(value, books, bookCopies);
                    break;
                case "author_id":
                    authorSearchStrategy.search(value, books, bookCopies);
                    break;
                case "publisher":
                    publisherSearchStrategy.search(value, books, bookCopies);
                    break;
                default:
                    System.out.println("Invalid search attribute: " + attribute);
            }
        } finally {
            libraryLock.unlock();
        }
    }
}
