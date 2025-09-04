package com.interview.problem.parkinglot.strategy.payment;

public class CreditCardPaymentStrategy implements PaymentStrategy {
    @Override
    public void makePayment(final double amount) {
        System.out.println("Processed credit card payment of Rs. " + amount);
    }
}
