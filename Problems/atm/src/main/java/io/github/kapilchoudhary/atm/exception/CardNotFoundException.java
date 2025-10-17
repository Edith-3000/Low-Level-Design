package io.github.kapilchoudhary.atm.exception;

public class CardNotFoundException extends RuntimeException {

    public CardNotFoundException(String cardNumber) {
        super("Card with number: " + cardNumber + " not found");
    }
}
