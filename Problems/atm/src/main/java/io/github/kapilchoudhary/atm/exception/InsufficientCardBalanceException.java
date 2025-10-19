package io.github.kapilchoudhary.atm.exception;

public class InsufficientCardBalanceException extends RuntimeException {

    public InsufficientCardBalanceException(double cardBalance) {
        super("Card balance: ₹ " + cardBalance + " is insufficient.");
    }
}
