package io.github.kapilchoudhary.atm.exception;

public class InsufficientDenominationsException extends RuntimeException {

    public InsufficientDenominationsException() {
        super("ATM not having sufficient note counts to serve requests.");
    }
}
