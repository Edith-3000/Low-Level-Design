package org.example.splitwise.exceptions;

public class ExpenseAlreadyExistsException extends RuntimeException {
    public ExpenseAlreadyExistsException(String message) {
        super(message);
    }
}
