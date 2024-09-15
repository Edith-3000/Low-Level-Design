package org.example.onlineshopping.interfaces.notification;

import lombok.NonNull;
import org.example.onlineshopping.models.Order;

public class SMSNotification implements INotificationObserver {
    @Override
    public void update(@NonNull final Order order) {
        System.out.println("Sending sms to user: " + order.getUser().getProfile().getPhoneNumber()
                + "for Order ID: " + order.getId()
                + " in " + order.getStatus() + " state");
    }
}
