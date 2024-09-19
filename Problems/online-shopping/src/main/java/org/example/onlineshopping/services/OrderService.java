package org.example.onlineshopping.services;

import lombok.NonNull;
import org.example.onlineshopping.enums.OrderStatus;
import org.example.onlineshopping.models.*;
import org.example.onlineshopping.models.PaymentMethod.PaymentMethod;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.UUID;

public class OrderService {
    private static volatile OrderService instance;
    private final InventoryService inventoryService = InventoryService.getInstance();
    private final ReservationService reservationService = ReservationService.getInstance();
    private final NotificationService notificationService = NotificationService.getInstance();

    private OrderService() {};

    public static OrderService getInstance() {
        if (instance == null) {
            synchronized (OrderService.class) {
                if (instance == null) {
                    instance = new OrderService();
                }
            }
        }

        return instance;
    }

    public Order placeOrder(@NonNull final ShoppingCart shoppingCart, @NonNull final User user, @NonNull final PaymentMethod paymentMethod) {
        // Check if all products are reserved before placing the order
        for (Map.Entry<Product, Integer> entry: shoppingCart.getItems().entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();

            // Ensure product was reserved by the user
            Reservation reservation = reservationService.getReservation(user, product);

            if (reservation == null) {
                System.out.println("Reservation for Product: " + product.getName() + " for user: " + user.getName() + " not found, aborting order placement");
                return null;
            }

            if (reservation.getQuantity() != quantity) {
                System.out.println("Product " + product.getName() + " is not fully reserved for user " + user.getName() + ", aborting order placement");
                return null;
            }
        }

        // Process payment
        paymentMethod.processPayment(shoppingCart.cartTotal());

        // Permanently reduce product quantities for reserved items
        for (Map.Entry<Product, Integer> entry: shoppingCart.getItems().entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();

            if (!inventoryService.reduceProductQuantity(product.getId(), quantity)) {
                System.out.println("Error reducing product quantity for: " + product.getName() + " for user: " + user.getName() + ", aborting order placement");
                return null; // Abort if quantity reduction fails
            }

            reservationService.releaseReservation(user, product);
        }

        String orderId = generateOrderId();

        Order order = new Order(orderId, user, shoppingCart);
        user.addOrder(order);

        System.out.println(orderId + " placed for user: " + user.getName() + " at "
                + ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));

        notificationService.notifyObservers(order);

        return order;
    }

    private String generateOrderId() {
        return "ORDER-" + UUID.randomUUID();
    }

    public void shipOrder(@NonNull final Order order) {
        order.setStatus(OrderStatus.SHIPPED);
        notificationService.notifyObservers(order);
    }

    public void deliverOrder(@NonNull final Order order) {
        order.setStatus(OrderStatus.DELIVERED);
        notificationService.notifyObservers(order);
    }

    public void cancelOrder(@NonNull final Order order) {
        order.setStatus(OrderStatus.CANCELLED);
        notificationService.notifyObservers(order);
    }
}
