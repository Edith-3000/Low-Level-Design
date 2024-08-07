package org.example.splitwise.models;

import org.example.splitwise.exceptions.NullSplitException;
import org.example.splitwise.models.splittype.EqualSplit;
import org.example.splitwise.models.splittype.ExactSplit;
import org.example.splitwise.models.splittype.PercentSplit;
import org.example.splitwise.models.splittype.Split;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Expense {
    private final String id;
    private final String description;
    private final double amount;
    private final User paidBy;
    private final List<Split> splits;

    public Expense(String id, String description, double amount, User paidBy) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.paidBy = paidBy;
        splits = new CopyOnWriteArrayList<>();
    }

    public String getId() {
        return this.id;
    }

    public String getDescription() {
        return this.description;
    }

    public double getAmount() {
        return this.amount;
    }

    public User getPaidBy() {
        return this.paidBy;
    }

    public void addSplit(Split split) {
        if (split == null) {
            throw new NullSplitException("Split cannot be null");
        }

        this.splits.add(split);
    }

    public List<Split> getSplits() {
        return Collections.unmodifiableList(this.splits);
    }

    public void splitExpense() {
        if (splits.isEmpty()) {
            throw new IllegalArgumentException(String.format("No splits in Expense: %s to split", this.id));
        }

        // Assuming all splits in an expense are of same type (i.e. either all are EqualSplit or ExactSplit or PercentSplit)
        double equalSplitAmount = amount / splits.size();
        double exactSplitTotalAmount = 0.0;
        double percentSplitTotal = 0.0;

        for (Split split: splits) {
            if (split instanceof EqualSplit) {
                split.setAmount(equalSplitAmount);
            } else if (split instanceof ExactSplit) {
                exactSplitTotalAmount += split.getAmount();
            } else if (split instanceof PercentSplit percentSplit) {
                percentSplitTotal += percentSplit.getPercent();
                split.setAmount(percentSplit.getPercent() / 100.0 * amount);
            }
        }

        if (exactSplitTotalAmount > amount) {
            throw new RuntimeException(String.format("Exact split amount summation across all splits exceeds amount of Expense: %s", this.id));
        } else if (percentSplitTotal > 100.0) {
            throw new RuntimeException(String.format("Total percent split across all splits exceeds 100%% for Expense: %s", this.id));
        }
    }
}
