package io.github.kapilchoudhary.atm.exception;

public class NoCardException extends RuntimeException {

    public NoCardException() {
        super("No card found in ATM.");
    }
}
