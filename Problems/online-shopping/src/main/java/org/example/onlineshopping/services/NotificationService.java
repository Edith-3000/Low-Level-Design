package org.example.onlineshopping.services;

import lombok.NonNull;
import org.example.onlineshopping.interfaces.notification.INotificationObserver;
import org.example.onlineshopping.models.Order;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class NotificationService {
    private static volatile NotificationService instance;
    private final List<INotificationObserver> observers;

    private NotificationService() {
        observers = new CopyOnWriteArrayList<>();
    }

    public static NotificationService getInstance() {
        if (instance == null) {
            synchronized (NotificationService.class) {
                if (instance == null) {
                    instance = new NotificationService();
                }
            }
        }

        return instance;
    }

    public void register(@NonNull final INotificationObserver observer) {
        observers.add(observer);
    }

    public void unregister(@NonNull final INotificationObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(Order order) {
        for (INotificationObserver observer: observers) {
            observer.update(order);
        }
    }
}
