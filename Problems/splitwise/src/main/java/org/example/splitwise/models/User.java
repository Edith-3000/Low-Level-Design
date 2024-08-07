package org.example.splitwise.models;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class User {
    private final String id;
    private final String name;
    private final String email;
    private final Map<String, Double> balances;

    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        balances = new ConcurrentHashMap<>();
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public Map<String, Double> getBalances() {
        return Collections.unmodifiableMap(this.balances);
    }

    public void updateBalance(String userId, double amount) {
        balances.put(userId, balances.getOrDefault(userId, 0.0) + amount);
    }

    public void settleBalance(String userId) {
        if (!balances.containsKey(userId) || (balances.get(userId) == 0.0)) {
            throw new RuntimeException(String.format("User: %s has no unsettled balance with User: %s", userId, this.id));
        }

        balances.put(userId, 0.0);
    }
}
