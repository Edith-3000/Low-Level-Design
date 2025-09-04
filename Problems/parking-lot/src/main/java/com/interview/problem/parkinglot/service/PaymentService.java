package com.interview.problem.parkinglot.service;

import com.interview.problem.parkinglot.strategy.payment.PaymentStrategy;
import lombok.Getter;
import lombok.Setter;

public class PaymentService {
    @Getter
    private PaymentStrategy paymentStrategy;

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public void makePayment(final double amount) {
        paymentStrategy.makePayment(amount);
    }
}
