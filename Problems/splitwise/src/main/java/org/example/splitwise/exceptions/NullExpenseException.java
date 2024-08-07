package org.example.splitwise.exceptions;

public class NullExpenseException extends RuntimeException {
    public NullExpenseException(String message) {
        super(message);
    }
}
