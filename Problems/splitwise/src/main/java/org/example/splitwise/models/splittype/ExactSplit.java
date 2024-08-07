package org.example.splitwise.models.splittype;

import org.example.splitwise.models.User;

public class ExactSplit extends Split {
    public ExactSplit(User user, double amount) {
        super(user);
        this.amount = amount;
    }
}
