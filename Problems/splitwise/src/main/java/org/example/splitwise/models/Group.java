package org.example.splitwise.models;

import org.example.splitwise.exceptions.ExpenseAlreadyExistsException;
import org.example.splitwise.exceptions.NullExpenseException;
import org.example.splitwise.exceptions.NullUserException;
import org.example.splitwise.exceptions.UserAlreadyExistsException;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Group {
    private final String id;
    private final String name;
    private final List<User> members;
    private final List<Expense> expenses;

    public Group(String id, String name) {
        this.id = id;
        this.name = name;

        this.members = new CopyOnWriteArrayList<>();
        this.expenses = new CopyOnWriteArrayList<>();
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void addMember(User user) {
        if (user == null) {
            throw new NullUserException("User cannot be null");
        } else if (userExists(user.getId())) {
            throw new UserAlreadyExistsException(String.format("User ID: %s already exists in Group ID: %s", user.getId(), this.id));
        }

        members.add(user);
    }

    private boolean userExists(String userId) {
        return this.members.stream()
                .anyMatch(user -> user.getId().equals(userId));
    }

    public void addExpense(Expense expense) {
        if (expense == null) {
            throw new NullExpenseException("Expense cannot be null");
        } else if (expenseExists(expense.getId())) {
            throw new ExpenseAlreadyExistsException(String.format("Expense ID: %s already exists in Group ID: %s", expense.getId(), this.id));
        }

        expenses.add(expense);
    }

    public boolean expenseExists(String expenseId) {
        return this.expenses.stream()
                .anyMatch(expense -> expense.getId().equals(expenseId));
    }

    public List<User> getMembers() {
        return Collections.unmodifiableList(this.members);
    }

    public List<Expense> getExpenses() {
        return Collections.unmodifiableList(this.expenses);
    }
}
