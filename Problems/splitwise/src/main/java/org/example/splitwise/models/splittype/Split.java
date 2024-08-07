package org.example.splitwise.models.splittype;

import org.example.splitwise.models.User;

public class Split {
    private final User user;
    protected double amount;

    Split(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
