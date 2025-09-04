package com.interview.problem.parkinglot.strategy.payment;

public class CashPaymentStrategy implements PaymentStrategy {
    @Override
    public void makePayment(final double amount) {
        System.out.println("Processed cash payment of Rs. " + amount);
    }
}
