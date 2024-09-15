package org.example.onlineshopping;

import org.example.onlineshopping.interfaces.notification.EmailNotification;
import org.example.onlineshopping.interfaces.notification.INotificationObserver;
import org.example.onlineshopping.interfaces.notification.SMSNotification;
import org.example.onlineshopping.models.*;
import org.example.onlineshopping.models.PaymentMethod.CreditCardPayment;
import org.example.onlineshopping.models.PaymentMethod.PayPalPayment;
import org.example.onlineshopping.models.PaymentMethod.PaymentMethod;
import org.example.onlineshopping.services.OnlineShoppingService;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        OnlineShoppingService shoppingService = OnlineShoppingService.getInstance();
        ExecutorService executor = Executors.newFixedThreadPool(10);  // A thread pool of 10 users

        // Add product categories
        ProductCategory electronics = shoppingService.addProductCategory("Electronics");
        ProductCategory clothing = shoppingService.addProductCategory("Clothing");

        // Add products
        Product laptop = shoppingService.addProduct("P1", "Laptop", "Gaming Laptop", 1500.0, 5, electronics);
        Product phone = shoppingService.addProduct("P2", "Phone", "Smartphone", 900.0, 10, electronics);

        Product tshirt = shoppingService.addProduct("P3", "T-Shirt", "Plain White T-Shirt", 20.0, 50, clothing);

        // Register users
        User user1 = shoppingService.registerUser("U1", "Bhola", new UserProfile("India", "bhola@example.com", "1234567890"));
        User user2 = shoppingService.registerUser("U2", "Shankar", new UserProfile("USA", "shankar@example.com", "0987654321"));
        User user3 = shoppingService.registerUser("U3", "Bob", new UserProfile("Israel", "bob@example.com", "9876543210"));

        // Create shopping carts
        ShoppingCart cart1 = new ShoppingCart();
        ShoppingCart cart2 = new ShoppingCart();
        ShoppingCart cart3 = new ShoppingCart();

        // Payment methods
        PaymentMethod creditCardPayment = new CreditCardPayment();
        PaymentMethod payPalPayment = new PayPalPayment();

        // Notification providers
        INotificationObserver emailNotifier = new EmailNotification();
        INotificationObserver smsNotifier = new SMSNotification();
        shoppingService.registerNotifier(emailNotifier);
        shoppingService.registerNotifier(smsNotifier);

        // Simulate user actions in separate threads
        executor.submit(() -> simulateShoppingExperience(shoppingService, user1, cart1, laptop, 2, creditCardPayment));
        executor.submit(() -> simulateShoppingExperience(shoppingService, user2, cart2, phone, 1, payPalPayment));
        executor.submit(() -> simulateShoppingExperience(shoppingService, user3, cart3, tshirt, 5, creditCardPayment));

        // Shutdown the executor and wait for all threads to finish
        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.MINUTES)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    // Simulates a user shopping experience
    public static void simulateShoppingExperience(OnlineShoppingService shoppingService, User user, ShoppingCart cart,
                                                  Product product, int quantity, PaymentMethod paymentMethod) {
        // Add to cart and reserve product
        shoppingService.addToCart(cart, product, quantity, user);

        // Random sleep to simulate browsing time
        sleepRandomTime(100, 500);

        // Place order
        shoppingService.placeOrder(cart, user, paymentMethod);
    }

    // Simulate a random delay
    public static void sleepRandomTime(int minMillis, int maxMillis) {
        Random random = new Random();
        int delay = random.nextInt(maxMillis - minMillis + 1) + minMillis; // Random delay between min and max
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();  // Properly handle interruptions
        }
    }
}
