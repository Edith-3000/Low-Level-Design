package org.example.onlineshopping.services;

import lombok.NonNull;
import org.example.onlineshopping.models.Product;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class InventoryService {
    private static volatile InventoryService instance;
    private final Map<String, Product> products;

    private InventoryService() {
        products = new ConcurrentHashMap<>();
    }

    public static InventoryService getInstance() {
        if (instance == null) {
            synchronized (InventoryService.class) {
                if (instance == null) {
                    instance = new InventoryService();
                }
            }
        }

        return instance;
    }

    public void addProduct(@NonNull final Product product) {
        products.putIfAbsent(product.getId(), product);
    }

    public List<Product> getProducts() {
        return List.copyOf(products.values());
    }

    public boolean isProductAvailable(@NonNull final String productId, final int quantity) {
        Product product = products.get(productId);

        if (product != null) {
            synchronized (product.getLock()) {
                int availableQuantity = product.getQuantity() - product.getReservedQuantity();
                return (availableQuantity >= quantity);
            }
        }

        return false;
    }

    // Reserve product quantity (temporarily reduce available stock)
    public boolean reserveProductQuantity(@NonNull final String productId, final int quantity) {
        Product product = products.get(productId);

        if (product != null && isProductAvailable(productId, quantity)) {
            synchronized (product.getLock()) {
                product.reserveQuantity(quantity);
            }
            return true;
        }

        return false;
    }

    // Release reserved product quantity (restore stock if reservation is cancelled)
    public void releaseReservedQuantity(@NonNull final String productId, final int quantity) {
        Product product = products.get(productId);

        if (product != null) {
            synchronized (product.getLock()) {
                product.releaseReservedQuantity(quantity);
            }
        }
    }

    // Permanently reduce product quantity (for order placement)
    public boolean reduceProductQuantity(@NonNull final String productId, final int quantity) {
        Product product = products.get(productId);

        if (product != null) {
            synchronized (product.getLock()) {
                return product.reduceQuantity(quantity);  // Final reduction after order placement
            }
        }

        return false;
    }
}
