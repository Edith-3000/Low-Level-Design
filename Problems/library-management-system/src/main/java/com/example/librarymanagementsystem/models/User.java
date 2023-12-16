package com.example.librarymanagementsystem.models;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String userID;
    private List<String> borrowedBookCopyIDs;

    public User(String userID) {
        this.userID = userID;
        this.borrowedBookCopyIDs = new ArrayList<>();
    }

    public int countOfBorrowedBookCopyIDs() {
        return borrowedBookCopyIDs.size();
    }

    public void addBorrowedBookCopyID(String bookCopyID) {
        borrowedBookCopyIDs.add(bookCopyID);
    }

    public void removeBorrowedBookCopyID(String bookCopyID) {
        borrowedBookCopyIDs.remove(bookCopyID);
    }

    public String getUserID() {
        return userID;
    }

    public List<String> getBorrowedBookCopyIDs() {
        return borrowedBookCopyIDs;
    }
}
