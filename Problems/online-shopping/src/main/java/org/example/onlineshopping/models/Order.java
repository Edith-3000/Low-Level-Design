package org.example.onlineshopping.models;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.example.onlineshopping.enums.OrderStatus;

public class Order {
    @Getter
    private final String id;
    @Getter
    private final User user;
    @Getter
    private final ShoppingCart shoppingCart;
    @Getter @Setter
    private OrderStatus status;

    public Order(@NonNull final String id, @NonNull final User user, @NonNull final ShoppingCart shoppingCart) {
        this.id = id;
        this.user = user;
        this.shoppingCart = shoppingCart;
        this.status = OrderStatus.PLACED;
    }
}
