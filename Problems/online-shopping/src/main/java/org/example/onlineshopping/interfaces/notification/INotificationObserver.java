package org.example.onlineshopping.interfaces.notification;

import lombok.NonNull;
import org.example.onlineshopping.models.Order;

public interface INotificationObserver {
    void update(@NonNull final Order order);
}
