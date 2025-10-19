package io.github.kapilchoudhary.atm.exception;

public class InsufficientATMBalanceException extends RuntimeException {

    public InsufficientATMBalanceException(double totalCashAvailable) {
        super("ATM has insufficient balance: ₹ " + totalCashAvailable);
    }
}
