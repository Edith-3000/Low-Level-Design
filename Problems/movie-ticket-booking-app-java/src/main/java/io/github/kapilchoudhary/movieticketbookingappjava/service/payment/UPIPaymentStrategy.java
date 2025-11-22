package io.github.kapilchoudhary.movieticketbookingappjava.service.payment;

public class UPIPaymentStrategy implements PaymentStrategy {
    @Override
    public boolean processPayment() {
        return true;
    }
}
