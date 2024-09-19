package org.example.onlineshopping.interfaces.notification;

import lombok.NonNull;
import org.example.onlineshopping.models.Order;

public class EmailNotification implements INotificationObserver {
    @Override
    public void update(@NonNull final Order order) {
        System.out.println("Sending email to user: " + order.getUser().getProfile().getEmail()
                + " for Order ID: " + order.getId()
                + " in " + order.getStatus() + " state");
    }
}
