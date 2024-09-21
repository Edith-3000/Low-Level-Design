package org.example.onlineshopping.models;

import lombok.Getter;
import lombok.NonNull;

public class Product {
    @Getter
    private final String id;
    @Getter
    private final String name;
    @Getter
    private final String description;
    @Getter
    private final double cost;
    private int quantity;
    private int reservedQuantity;
    @Getter
    private final ProductCategory category;
    @Getter
    private final Object lock;

    public Product(@NonNull final String id, @NonNull final String name, @NonNull final String description,
                   final double cost, final int quantity, @NonNull final ProductCategory category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.quantity = quantity;
        this.reservedQuantity = 0;
        this.category = category;
        lock = new Object();
    }

    public int getQuantity() {
        return quantity;
    }

    public int getReservedQuantity() {
        return reservedQuantity;
    }

    // Temporarily reserve quantity (reduce available stock)
    public void reserveQuantity(final int quantity) {
        this.reservedQuantity += quantity;
    }

    // Release reserved quantity (restore available stock)
    public void releaseReservedQuantity(final int quantityToRelease) {
        if (reservedQuantity >= quantityToRelease) {
            reservedQuantity -= quantityToRelease;
        }
    }

    // Permanently reduce quantity after order placement
    public boolean reduceQuantity(final int quantityToReduce) {
        if (this.quantity >= quantityToReduce) {
            this.quantity -= quantityToReduce;
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        synchronized (lock) {
            return "Product {" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", description='" + description + '\'' +
                    ", cost=" + cost +
                    ", quantityAvailable=" + (quantity - reservedQuantity) +
                    ", category=" + category +
                    '}';
        }
    }
}
