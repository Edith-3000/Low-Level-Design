package org.example.onlineshopping.models;

import lombok.Getter;
import lombok.NonNull;

public class Reservation {
    private final User user;
    private final Product product;
    @Getter
    private final int quantity;

    public Reservation(@NonNull final User user, @NonNull final Product product, final int quantity) {
        this.user = user;
        this.product = product;
        this.quantity = quantity;
    }
}
