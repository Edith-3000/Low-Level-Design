package com.example.librarymanagementsystem.services.search;

import com.example.librarymanagementsystem.models.Book;
import com.example.librarymanagementsystem.models.BookCopy;
import com.example.librarymanagementsystem.utils.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TitleSearchStrategy implements SearchStrategy {
    @Override
    public void search(String query, List<Book> books, List<BookCopy> bookCopies) {
        List<Book> unmodifiableBooks = Collections.unmodifiableList(books);
        List<BookCopy> unmodifiableBookCopies = Collections.unmodifiableList(bookCopies);

        Book book = null;
        for(Book value: unmodifiableBooks) {
            if(value.getTitle().equals(query)) {
                book = value;
                break;
            }
        }

        if(book == null) {
            return;
        }

        List<BookCopy> searchedBookCopies = new ArrayList<>();
        for(BookCopy value: unmodifiableBookCopies) {
            if(value.getBookID() == book.getBookID()) {
                searchedBookCopies.add(value);
            }
        }

        searchedBookCopies.sort(Comparator.comparingInt(BookCopy::getRackNumber));

        for(BookCopy value: searchedBookCopies) {
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
