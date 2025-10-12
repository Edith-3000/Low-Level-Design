package io.github.kapilchoudhary.atm.model;

import lombok.Getter;
import lombok.NonNull;

public class Card {

    @Getter
    private final String cardNumber;

    @Getter
    private final String pin;

    @Getter
    private final String accountNumber;

    public Card(@NonNull final String cardNumber, @NonNull String pin, @NonNull final String accountNumber) {
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.accountNumber = accountNumber;
    }
}
