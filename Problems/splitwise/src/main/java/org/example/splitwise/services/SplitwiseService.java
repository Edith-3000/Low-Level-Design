package org.example.splitwise.services;

import org.example.splitwise.exceptions.*;
import org.example.splitwise.models.Expense;
import org.example.splitwise.models.Group;
import org.example.splitwise.models.Transaction;
import org.example.splitwise.models.User;
import org.example.splitwise.models.splittype.Split;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class SplitwiseService {
    private static volatile SplitwiseService instance;
    private final Map<String, User> users;
    private final Map<String, Group> groups;

    private final List<Transaction> transactions;

    private final String TRANSACTION_ID_PREFIX = "TXN";
    private final AtomicInteger transactionCounter;

    private SplitwiseService() {
        users = new ConcurrentHashMap<>();
        groups = new ConcurrentHashMap<>();
        transactions = new CopyOnWriteArrayList<>();
        transactionCounter = new AtomicInteger(0);
    }

    public static SplitwiseService getInstance() {
        if (instance == null) {
            synchronized (SplitwiseService.class) {
                if (instance == null) {
                    instance = new SplitwiseService();
                }
            }
        }

        return instance;
    }

    public void addUser(User user) {
        if (user == null) {
            throw new NullUserException("User cannot be null");
        } else if (users.containsKey(user.getId())) {
            throw new UserAlreadyExistsException(String.format("User ID: %s already exists in system", user.getId()));
        }

        users.put(user.getId(), user);
    }

    public void addGroup(Group group) {
        if (group == null) {
            throw new NullGroupException("Group cannot be null");
        } else if (groups.containsKey(group.getId())) {
            throw new GroupAlreadyExistsException(String.format("Group ID: %s already exists in system", group.getId()));
        }

        groups.put(group.getId(), group);
    }

    public void addExpense(String groupId, Expense expense) {
        if (expense == null) {
            throw new NullExpenseException("Expense cannot be null");
        } else if (!groups.containsKey(groupId)) {
            throw new IllegalArgumentException(String.format("Group ID: %s does not exist in system", groupId));
        } else if (groups.get(groupId).expenseExists(expense.getId())) {
            throw new ExpenseAlreadyExistsException(String.format("Expense ID: %s already exists in Group ID: %s", expense.getId(), groupId));
        }

        Group group = groups.get(groupId);

        group.addExpense(expense);
        expense.splitExpense();
        updateBalances(expense);
    }

    private void updateBalances(Expense expense) {
        if (expense == null) {
            throw new NullExpenseException("Expense cannot be null");
        }

        User expensePaidBy = expense.getPaidBy();

        for (Split split: expense.getSplits()) {
            User splitUser = split.getUser();
            double amount = split.getAmount();

            if (!splitUser.equals(expensePaidBy)) {
                splitUser.updateBalance(expensePaidBy.getId(), amount);
                expensePaidBy.updateBalance(splitUser.getId(), -amount);
            }
        }
    }

    public void settleBalance(String userId1, String userId2) {
        if (!users.containsKey(userId1)) {
            throw new UserNotExistsException(String.format("User with ID: %s does not exist in system", userId1));
        } else if (!users.containsKey(userId2)) {
            throw new UserNotExistsException(String.format("User with ID: %s does not exist in system", userId1));
        }

        User user1 = users.get(userId1);
        User user2 = users.get(userId2);

        double balanceAmount = user1.getBalances().getOrDefault(userId2, 0.0);

        if (balanceAmount > 0.0) {
            recordTransaction(user1, user2, balanceAmount);
            user1.settleBalance(userId2);
            user2.settleBalance(userId1);
        } else if (balanceAmount < 0.0) {
            recordTransaction(user2, user1, balanceAmount);
            user1.settleBalance(userId2);
            user2.settleBalance(userId1);
        }
    }

    private void recordTransaction(User sender, User receiver, double amount) {
        String transactionId = generateTransactionId();
        Transaction transaction = new Transaction(transactionId, sender, receiver, amount, ZonedDateTime.now());
        transactions.add(transaction);
    }

    private String generateTransactionId() {
        int transactionNumber = transactionCounter.incrementAndGet();
        return TRANSACTION_ID_PREFIX + "-" + String.format("%06d", transactionNumber);
    }
}
