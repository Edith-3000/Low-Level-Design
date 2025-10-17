package io.github.kapilchoudhary.atm.exception;

public class CardInactiveException extends RuntimeException {

    public CardInactiveException(String cardNumber) {
        super("Card with number: " + cardNumber + " is inactive");
    }
}
