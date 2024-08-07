package org.example.splitwise;

import org.example.splitwise.models.Expense;
import org.example.splitwise.models.Group;
import org.example.splitwise.models.User;
import org.example.splitwise.models.splittype.EqualSplit;
import org.example.splitwise.models.splittype.ExactSplit;
import org.example.splitwise.models.splittype.PercentSplit;
import org.example.splitwise.services.SplitwiseService;

import java.util.Arrays;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        SplitwiseService splitwiseService = SplitwiseService.getInstance();

        User user1 = new User("1", "Kapil", "kapil@gmail.com");
        User user2 = new User("2", "Aditya", "aditya@gmail.com");
        User user3 = new User("3", "Yogi", "yogi@gmail.com");
        User user4 = new User("4", "Butani", "butani@gmail.com");

        splitwiseService.addUser(user1);
        splitwiseService.addUser(user2);
        splitwiseService.addUser(user3);
        splitwiseService.addUser(user4);

        Thread thread1 = new Thread(() -> {
            Group group = new Group("1", "Apartment");
            group.addMember(user1);
            group.addMember(user2);
            group.addMember(user3);

            splitwiseService.addGroup(group);

            Expense expense = new Expense("1", "Rent", 18000, user1);

            EqualSplit equalSplit1 = new EqualSplit(user1);
            EqualSplit equalSplit2 = new EqualSplit(user2);
            EqualSplit equalSplit3 = new EqualSplit(user3);

            expense.addSplit(equalSplit1);
            expense.addSplit(equalSplit2);
            expense.addSplit(equalSplit3);

            splitwiseService.addExpense(group.getId(), expense);

            splitwiseService.settleBalance(user1.getId(), user3.getId());
        });

        Thread thread2 = new Thread(() -> {
            Group group = new Group("2", "Travel");
            group.addMember(user2);
            group.addMember(user4);
            group.addMember(user1);

            splitwiseService.addGroup(group);

            Expense expense = new Expense("2", "Kedarnath", 27000, user2);

            ExactSplit exactSplit1 = new ExactSplit(user2, 5000);
            ExactSplit exactSplit2 = new ExactSplit(user4, 17000);
            ExactSplit exactSplit3 = new ExactSplit(user1, 5000);

            expense.addSplit(exactSplit1);
            expense.addSplit(exactSplit2);
            expense.addSplit(exactSplit3);

            splitwiseService.addExpense(group.getId(), expense);

            splitwiseService.settleBalance(user4.getId(), user1.getId());
        });

        Thread thread3 = new Thread(() -> {
            Group group = new Group("3", "Office");
            group.addMember(user2);
            group.addMember(user3);
            group.addMember(user4);

            splitwiseService.addGroup(group);

            Expense expense1 = new Expense("1", "Cricket Tournament", 50000, user3);

            PercentSplit percentSplit1 = new PercentSplit(user2, 60);
            PercentSplit percentSplit2 = new PercentSplit(user3, 15);
            PercentSplit percentSplit3 = new PercentSplit(user4, 25);

            expense1.addSplit(percentSplit1);
            expense1.addSplit(percentSplit2);
            expense1.addSplit(percentSplit3);

            splitwiseService.addExpense(group.getId(), expense1);

            Expense expense2 = new Expense("2", "Team Lunch", 17000, user2);

            EqualSplit equalSplit1 = new EqualSplit(user2);
            EqualSplit equalSplit2 = new EqualSplit(user3);
            EqualSplit equalSplit3 = new EqualSplit(user4);

            expense2.addSplit(equalSplit1);
            expense2.addSplit(equalSplit2);
            expense2.addSplit(equalSplit3);

            splitwiseService.addExpense(group.getId(), expense2);

            splitwiseService.settleBalance(user2.getId(), user3.getId());
            splitwiseService.settleBalance(user3.getId(), user4.getId());
        });

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (User user: Arrays.asList(user1, user2, user3, user4)) {
            System.out.printf("Balance(s) for User - ID: %s, Name: %s, Email: %s%n", user.getId(), user.getName(), user.getEmail());

            for (Map.Entry<String, Double> entry: user.getBalances().entrySet()) {
                System.out.printf("--> Balance with %s: %f", entry.getKey(), entry.getValue());
                System.out.println();
            }

            System.out.println();
        }
    }
}
