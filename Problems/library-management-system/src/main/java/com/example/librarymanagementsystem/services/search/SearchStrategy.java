package com.example.librarymanagementsystem.services.search;

import com.example.librarymanagementsystem.models.Book;
import com.example.librarymanagementsystem.models.BookCopy;

import java.util.List;

public interface SearchStrategy {
    public void search(String query, List<Book> books, List<BookCopy> bookCopies);
}
