package org.example.splitwise.models;

import java.time.ZonedDateTime;

public class Transaction {
    private final String id;
    private final User sender;
    private final User receiver;
    private final double amount;
    private final ZonedDateTime time;

    public Transaction(String id, User sender, User receiver, double amount, ZonedDateTime time) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.time = time;
    }
}
