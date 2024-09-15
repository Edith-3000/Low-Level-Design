package org.example.onlineshopping.models.PaymentMethod;

public class PayPalPayment extends PaymentMethod {
    @Override
    protected void initiatePayment(double amount) {
        System.out.println("Initiating PayPal payment for amount: " + amount);
    }

    @Override
    protected void verifyPaymentDetails() {
        System.out.println("Verifying PayPal account...");
    }

    @Override
    protected void completePayment() {
        System.out.println("Completing PayPal payment...");
    }
}
