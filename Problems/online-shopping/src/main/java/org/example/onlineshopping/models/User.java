package org.example.onlineshopping.models;

import lombok.Getter;
import lombok.NonNull;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class User {
    @Getter
    private final String id;
    @Getter
    private final String name;
    @Getter
    private final UserProfile profile;
    private final List<Order> orderHistory;

    public User(@NonNull final String id, @NonNull final String name, @NonNull final UserProfile profile) {
        this.id = id;
        this.name = name;
        this.profile = profile;
        orderHistory = new CopyOnWriteArrayList<>();
    }

    public List<Order> getOrderHistory() {
        return Collections.unmodifiableList(orderHistory);
    }

    public void addOrder(@NonNull final Order order) {
        orderHistory.add(order);
    }
}
