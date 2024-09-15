package org.example.onlineshopping.services;

import lombok.NonNull;
import org.example.onlineshopping.models.Product;
import org.example.onlineshopping.models.Reservation;
import org.example.onlineshopping.models.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ReservationService {
    private static volatile ReservationService instance;
    private final Map<String, Reservation> reservations;
    private final ScheduledExecutorService scheduler;

    private ReservationService() {
        this.reservations = new ConcurrentHashMap<>();
        this.scheduler = Executors.newScheduledThreadPool(1);
    }

    public static ReservationService getInstance() {
        if (instance == null) {
            synchronized (ReservationService.class) {
                if (instance == null) {
                    instance = new ReservationService();
                }
            }
        }

        return instance;
    }

    public boolean reserveProduct(@NonNull final User user, @NonNull final Product product, final int quantity) {
        String productId = product.getId();
        String reservationKey = getReservationKey(user.getId(), productId);

        // First, check product availability outside synchronized block
        if (!InventoryService.getInstance().isProductAvailable(productId, quantity)) {
            return false;
        }

        synchronized (product.getLock()) {
            // Double-check availability inside the synchronized block
            if (InventoryService.getInstance().reserveProductQuantity(productId, quantity)) {
                Reservation reservation = new Reservation(user, product, quantity);
                reservations.put(reservationKey, reservation);

                // Set a timer to release the reservation after 5 minutes (300 seconds)
                scheduler.schedule(() -> releaseReservation(user, product), 5, TimeUnit.MINUTES);

                return true;
            }
        }

        return false;
    }

    public void releaseReservation(@NonNull final User user, @NonNull final Product product) {
        String reservationKey = getReservationKey(user.getId(), product.getId());
        Reservation reservation = reservations.remove(reservationKey);

        if (reservation != null) {
            // Restore the reserved quantity in inventory
            InventoryService.getInstance().releaseReservedQuantity(product.getId(), reservation.getQuantity());
            System.out.println("Reservation for product: "
                    + product.getName() + " by user: " + user.getName()
                    + " for quantity: " + reservation.getQuantity()
                    + " has been released.");
        }
    }

    private String getReservationKey(@NonNull final String userId, @NonNull final String productId) {
        return "RK-" + userId + "-" + productId;
    }

    public Reservation getReservation(@NonNull final User user, @NonNull final Product product) {
        String reservationKey = getReservationKey(user.getId(), product.getId());
        return reservations.get(reservationKey);
    }
}
