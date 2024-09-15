package org.example.onlineshopping.models.PaymentMethod;

public abstract class PaymentMethod {
    public final void processPayment(final double amount) {
        initiatePayment(amount);
        verifyPaymentDetails();
        completePayment();
    }

    protected abstract void initiatePayment(final double amount);
    protected abstract void verifyPaymentDetails();
    protected abstract void completePayment();
}
