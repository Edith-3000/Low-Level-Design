package org.example.onlineshopping.models.PaymentMethod;

public class CreditCardPayment extends PaymentMethod {
    @Override
    protected void initiatePayment(double amount) {
        System.out.println("Initiating credit card payment for amount: " + amount);
    }

    @Override
    protected void verifyPaymentDetails() {
        System.out.println("Verifying credit card details...");
    }

    @Override
    protected void completePayment() {
        System.out.println("Completing credit card payment...");
    }
}
