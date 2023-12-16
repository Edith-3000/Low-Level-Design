package com.example.librarymanagementsystem.models;

import java.util.List;

public class Book {
    private int bookID;
    private String title;
    private List<String> authors;
    private List<String> publishers;
    private List<String> bookCopyIDs;

    public Book(int bookID, String title, List<String> authors, List<String> publishers, List<String> bookCopyIDs) {
        this.bookID = bookID;
        this.title = title;
        this.authors = authors;
        this.publishers = publishers;
        this.bookCopyIDs = bookCopyIDs;
    }

    public int getBookID() {
        return bookID;
    }

    public String getTitle() {
        return this.title;
    }

    public List<String> getAuthors() {
        return this.authors;
    }

    public List<String> getPublishers() {
        return this.publishers;
    }

    public void removeBookCopyID(String bookCopyID) {
        bookCopyIDs.remove(bookCopyID);
    }
}
