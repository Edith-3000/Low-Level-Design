package org.example.onlineshopping.services;

import lombok.NonNull;
import org.example.onlineshopping.interfaces.notification.INotificationObserver;
import org.example.onlineshopping.models.*;
import org.example.onlineshopping.models.PaymentMethod.PaymentMethod;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class OnlineShoppingService {
    private static volatile OnlineShoppingService instance;
    private final InventoryService inventoryService = InventoryService.getInstance();
    private final ReservationService reservationService = ReservationService.getInstance();
    private final OrderService orderService = OrderService.getInstance();
    private final NotificationService notificationService = NotificationService.getInstance();
    private final Map<String, User> users;
    private final List<ProductCategory> productCategories;

    private OnlineShoppingService() {
        users = new ConcurrentHashMap<>();
        productCategories = new CopyOnWriteArrayList<>();
    }

    public static OnlineShoppingService getInstance() {
        if (instance == null) {
            synchronized (OnlineShoppingService.class) {
                if (instance == null) {
                    instance = new OnlineShoppingService();
                }
            }
        }

        return instance;
    }

    public ProductCategory addProductCategory(@NonNull final String productCategoryName) {
        if (productCategories.stream().anyMatch(category -> category.getName().equals(productCategoryName))) {
            return null;
        }

        ProductCategory productCategory = new ProductCategory(productCategoryName);
        productCategories.add(productCategory);

        return productCategory;
    }

    public Product addProduct(@NonNull final String id, @NonNull final String name, @NonNull final String description,
                              final double cost, final int quantity, @NonNull final ProductCategory productCategory) {
        Product product = new Product(id, name, description, cost, quantity, productCategory);
        inventoryService.addProduct(product);
        return product;
    }

    public User registerUser(@NonNull final String userId, @NonNull final String userName, @NonNull final UserProfile userProfile) {
        if (users.containsKey(userId)) {
            return null;
        }

        User user = new User(userId, userName, userProfile);
        users.put(userId, user);

        // TODO: in order to ensure that the client cannot modify the returned user object, instead of
        //       directly returning it like this, return a defensive copy of it.
        return user;
    }

    public void addToCart(@NonNull final ShoppingCart cart, @NonNull final Product product, final int quantity, @NonNull final User user) {
        cart.addItem(product, quantity);

        if (reservationService.reserveProduct(user, product, quantity)) {
            System.out.println("SUCCESS to reserve product: " + product.getName()
                    + " for user: " + user.getName()
                    + " for quantity: " + quantity);
        } else {
            System.out.println("FAIL to reserve product: " + product.getName()
                    + " for user: " + user.getName()
                    + " for quantity: " + quantity);
        }
    }

    public void placeOrder(@NonNull final ShoppingCart shoppingCart, @NonNull final User user, @NonNull final PaymentMethod paymentMethod) {
        new Thread(() -> {
            Order order = orderService.placeOrder(shoppingCart, user, paymentMethod);

            if (order == null) {
                // Gracefully stop the thread if order is null
                return;
            }

            // Simulate random delay between order placement and shipping
            sleepRandomTime(100, 500); // Random delay between 100ms and 500ms

            // Ship the order
            orderService.shipOrder(order);

            // Simulate random delay between shipping and delivery
            sleepRandomTime(500, 1000); // Random delay between 500ms and 1 second

            // Deliver the order
            orderService.deliverOrder(order);
        }).start();
    }

    private void sleepRandomTime(int minMillis, int maxMillis) {
        Random random = new Random();
        int delay = random.nextInt(maxMillis - minMillis + 1) + minMillis; // Random delay between min and max
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();  // Properly handle interruptions
            throw new RuntimeException(e);
        }
    }

    public void registerNotifier(INotificationObserver notificationObserver) {
        notificationService.register(notificationObserver);
    }

    public void unregisterNotifier(INotificationObserver notificationObserver) {
        notificationService.unregister(notificationObserver);
    }
}
