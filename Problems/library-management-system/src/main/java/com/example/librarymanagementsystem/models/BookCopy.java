package com.example.librarymanagementsystem.models;

import java.time.LocalDate;

public class BookCopy {
    private String bookCopyID;
    private int bookID;
    private int rackNumber;
    private String borrowedBy;
    private LocalDate dueDate;

    public BookCopy(String bookCopyID, int bookID, int rackNumber, String borrowedBy, LocalDate dueDate) {
        this.bookCopyID = bookCopyID;
        this.bookID = bookID;
        this.rackNumber = rackNumber;
        this.borrowedBy = borrowedBy;
        this.dueDate = dueDate;
    }

    public int getRackNumber() {
        return rackNumber;
    }

    public void setRackNumber(int rackNumber) {
        this.rackNumber = rackNumber;
    }

    public String getBookCopyID() {
        return bookCopyID;
    }

    public int getBookID() {
        return bookID;
    }

    public String getBorrowedBy() {
        return borrowedBy;
    }

    public void setBorrowedBy(String borrowedBy) {
        this.borrowedBy = borrowedBy;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }
}
