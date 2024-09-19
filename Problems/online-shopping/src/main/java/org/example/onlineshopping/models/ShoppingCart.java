package org.example.onlineshopping.models;

import lombok.NonNull;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ShoppingCart {
    private final Map<Product, Integer> items;

    public ShoppingCart() {
        this.items = new ConcurrentHashMap<>();
    }

    public void addItem(@NonNull final Product product, final int quantity) {
        items.put(product, items.getOrDefault(product, 0) + quantity);
    }

    public Map<Product, Integer> getItems() {
        return Collections.unmodifiableMap(items);
    }

    public double cartTotal() {
        return items.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getCost() * entry.getValue())
                .sum();
    }
}
