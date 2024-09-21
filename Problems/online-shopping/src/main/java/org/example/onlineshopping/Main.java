package org.example.onlineshopping;

import org.example.onlineshopping.interfaces.notification.EmailNotification;
import org.example.onlineshopping.interfaces.notification.INotificationObserver;
import org.example.onlineshopping.interfaces.notification.SMSNotification;
import org.example.onlineshopping.interfaces.search.ISearchStrategy;
import org.example.onlineshopping.interfaces.search.NameSearchStrategy;
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
        ExecutorService executor = Executors.newFixedThreadPool(5);  // A thread pool of users

        // Add product categories
        ProductCategory electronics = shoppingService.addProductCategory("ELECTRONICS");
        ProductCategory clothing = shoppingService.addProductCategory("CLOTHING");
        ProductCategory books = shoppingService.addProductCategory("BOOKS");
        ProductCategory homeAppliances = shoppingService.addProductCategory("HOME APPLIANCES");
        ProductCategory furniture = shoppingService.addProductCategory("FURNITURE");
        ProductCategory toys = shoppingService.addProductCategory("TOYS");
        ProductCategory beauty = shoppingService.addProductCategory("BEAUTY");
        ProductCategory sports = shoppingService.addProductCategory("SPORTS");
        ProductCategory groceries = shoppingService.addProductCategory("GROCERIES");
        ProductCategory footwear = shoppingService.addProductCategory("FOOTWEAR");

        // Add products
        Product asusLaptop = shoppingService.addProduct("P1", "Asus Laptop", "Gaming Laptop", 1500.0, 5, electronics);
        Product oppoPhone = shoppingService.addProduct("P2", "Oppo Phone", "Smartphone", 900.0, 10, electronics);
        Product sonyHeadphones = shoppingService.addProduct("P3", "Sony Headphones", "Noise Cancelling Headphones", 200.0, 15, electronics);

        Product levisTshirt = shoppingService.addProduct("P4", "Levis T-Shirt", "Plain White T-Shirt", 20.0, 50, clothing);
        Product nikeShoes = shoppingService.addProduct("P5", "Nike Running Shoes", "Comfortable running shoes", 100.0, 30, footwear);
        Product rayBanSunglasses = shoppingService.addProduct("P6", "Ray-Ban Sunglasses", "Stylish sunglasses", 150.0, 20, clothing);

        Product coffeeMaker = shoppingService.addProduct("P7", "Coffee Maker", "Automatic coffee maker", 80.0, 10, homeAppliances);
        Product sofaSet = shoppingService.addProduct("P8", "Sofa Set", "Luxury sofa set", 1000.0, 3, furniture);
        Product diningTable = shoppingService.addProduct("P9", "Dining Table", "Wooden dining table", 600.0, 2, furniture);

        Product cricketBat = shoppingService.addProduct("P10", "Cricket Bat", "Professional cricket bat", 75.0, 40, sports);
        Product yogaMat = shoppingService.addProduct("P11", "Yoga Mat", "Anti-slip yoga mat", 25.0, 50, sports);
        Product proteinPowder = shoppingService.addProduct("P12", "Protein Powder", "Whey protein", 50.0, 30, groceries);

        Product fictionBook = shoppingService.addProduct("P13", "Fiction Book", "Best-selling novel", 15.0, 100, books);
        Product legoSet = shoppingService.addProduct("P14", "LEGO Set", "Creative building blocks", 40.0, 25, toys);
        Product hairDryer = shoppingService.addProduct("P15", "Hair Dryer", "High power hair dryer", 35.0, 20, beauty);


        // Register users
        User user1 = shoppingService.registerUser("U1", "Alice", new UserProfile("India", "alice@example.com", "1234567890"));
        User user2 = shoppingService.registerUser("U2", "Bob", new UserProfile("USA", "bob@example.com", "0987654321"));
        User user3 = shoppingService.registerUser("U3", "Charlie", new UserProfile("Israel", "charlie@example.com", "1111222233"));
        User user4 = shoppingService.registerUser("U4", "Dave", new UserProfile("Russia", "dave@example.com", "4444555566"));
        User user5 = shoppingService.registerUser("U5", "Eve", new UserProfile("Turkey", "eve@example.com", "7777888899"));

        // Create shopping carts
        ShoppingCart cart1 = new ShoppingCart();
        ShoppingCart cart2 = new ShoppingCart();
        ShoppingCart cart3 = new ShoppingCart();
        ShoppingCart cart4 = new ShoppingCart();
        ShoppingCart cart5 = new ShoppingCart();

        // Payment methods
        PaymentMethod creditCardPayment = new CreditCardPayment();
        PaymentMethod payPalPayment = new PayPalPayment();

        // Notification providers
        INotificationObserver emailNotifier = new EmailNotification();
        INotificationObserver smsNotifier = new SMSNotification();
        shoppingService.registerNotifier(emailNotifier);
        shoppingService.registerNotifier(smsNotifier);

        // Set the search strategy
        ISearchStrategy nameSearchStrategy = new NameSearchStrategy();
        shoppingService.setSearchStrategy(nameSearchStrategy);

        // Simulate user actions in separate threads
        executor.submit(() -> simulateShoppingExperience(shoppingService, user1, cart1, asusLaptop, 2, creditCardPayment));
        executor.submit(() -> simulateShoppingExperience(shoppingService, user2, cart2, nikeShoes, 1, payPalPayment));
        executor.submit(() -> simulateShoppingExperience(shoppingService, user3, cart3, fictionBook, 3, creditCardPayment));
        executor.submit(() -> simulateShoppingExperience(shoppingService, user4, cart4, coffeeMaker, 1, payPalPayment));
        executor.submit(() -> simulateShoppingExperience(shoppingService, user5, cart5, proteinPowder, 4, creditCardPayment));

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
        // Search
        shoppingService.search("Lapt");

        // Add to cart and reserve product
        shoppingService.addToCart(cart, product, quantity, user);

        // Random sleep to simulate browsing time
        sleepRandomTime(750, 8000);

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
