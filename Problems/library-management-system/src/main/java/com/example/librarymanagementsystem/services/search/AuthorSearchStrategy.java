package com.example.librarymanagementsystem.services.search;

import com.example.librarymanagementsystem.models.Book;
import com.example.librarymanagementsystem.models.BookCopy;
import com.example.librarymanagementsystem.utils.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AuthorSearchStrategy implements SearchStrategy {
    @Override
    public void search(String query, List<Book> books, List<BookCopy> bookCopies) {
        List<Book> unmodifiableBooks = Collections.unmodifiableList(books);
        List<BookCopy> unmodifiableBookCopies = Collections.unmodifiableList(bookCopies);

        List<Book> bookList = new ArrayList<>();
        List<BookCopy> searchedBookCopies = new ArrayList<>();

        for(Book value: unmodifiableBooks) {
            if(value.getAuthors().contains(query)) {
                bookList.add(value);

                for(BookCopy bookCopyValue: unmodifiableBookCopies) {
                    if(bookCopyValue.getBookID() == value.getBookID()) {
                        searchedBookCopies.add(bookCopyValue);
                    }
                }
            }
        }

        if(bookList.isEmpty()) {
            return;
        }

        searchedBookCopies.sort(Comparator.comparingInt(BookCopy::getRackNumber));

        for(BookCopy value: searchedBookCopies) {
            Book book = null;
            for(Book bookValue: bookList) {
                if(bookValue.getBookID() == value.getBookID()) {
                    book = bookValue;
                    break;
                }
            }

            assert book != null;

            System.out.print("Book Copy: " +
                    value.getBookCopyID() + " " +
                    book.getBookID() + " " +
                    book.getTitle() + " " +
                    Util.generateCommaSeparatedString(book.getAuthors()) + " " +
                    Util.generateCommaSeparatedString(book.getPublishers()) + " " +
                    value.getRackNumber() + " ");

            if(!value.getBorrowedBy().isEmpty()) {
                System.out.print(value.getBorrowedBy() + " " + value.getDueDate());
            }

            System.out.println();
        }
    }
}
