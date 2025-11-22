package io.github.kapilchoudhary.movieticketbookingappjava.service.payment;

public class DebitCardPaymentStrategy implements PaymentStrategy {
    @Override
    public boolean processPayment() {
        return false;
    }
}
