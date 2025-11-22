package io.github.kapilchoudhary.movieticketbookingappjava.controller;

import io.github.kapilchoudhary.movieticketbookingappjava.model.User;
import io.github.kapilchoudhary.movieticketbookingappjava.service.PaymentService;
import lombok.NonNull;

public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(@NonNull final PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public void processPayment(@NonNull String bookingId, @NonNull User user) throws Exception {
        paymentService.processPayment(bookingId, user);
    }
}
