package io.github.kapilchoudhary.atm.exception;

public class InvalidWithdrawAmountException extends RuntimeException {

    public InvalidWithdrawAmountException(double amount) {
        super("Withdraw amount: ₹ " + amount + " is invalid. \nAmount should be more than 0 and in multiple of 100");
    }
}
