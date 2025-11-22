package io.github.kapilchoudhary.movieticketbookingappjava.service;

import io.github.kapilchoudhary.movieticketbookingappjava.model.Booking;
import io.github.kapilchoudhary.movieticketbookingappjava.model.User;
import io.github.kapilchoudhary.movieticketbookingappjava.service.payment.PaymentStrategy;
import lombok.NonNull;
import lombok.Setter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PaymentService {

    private final int paymentFailuresAllowed;
    private final Map<String, Integer> bookingPaymentFailures;
    private final BookingService bookingService;

    @Setter
    private PaymentStrategy paymentStrategy;

    public PaymentService(
            final int paymentFailuresAllowed,
            @NonNull final PaymentStrategy paymentStrategy,
            @NonNull final BookingService bookingService
    ) {
        this.paymentFailuresAllowed = paymentFailuresAllowed;
        this.bookingPaymentFailures = new ConcurrentHashMap<>();
        this.paymentStrategy = paymentStrategy;
        this.bookingService = bookingService;
    }

    public void processPayment(@NonNull String bookingId, @NonNull User user) throws Exception {
        Booking booking = bookingService.getBookingById(bookingId);

        if (paymentStrategy.processPayment()) {
            bookingService.confirmBooking(booking, user);
        } else {
            bookingPaymentFailures.put(bookingId, bookingPaymentFailures.getOrDefault(bookingId, 0) + 1);

            if (bookingPaymentFailures.get(bookingId) > paymentFailuresAllowed) {
                bookingService.expireBooking(booking, user);
            }
        }
    }
}
