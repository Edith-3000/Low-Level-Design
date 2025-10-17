package io.github.kapilchoudhary.atm.exception;

public class InvalidPinException extends RuntimeException {

    public InvalidPinException(String pin) {
        super("Invalid PIN: " + pin + " entered");
    }
}
