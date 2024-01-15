package org.example.observerpattern;

import java.util.ArrayList;
import java.util.List;

public class MyTopic implements Subject {
    private final List<Observer> observers;
    private final Object mutex;
    private String message;
    private boolean changed;

    public MyTopic() {
        this.observers = new ArrayList<>();
        mutex = new Object();
        message = "";
        changed = false;
    }

    @Override
    public void register(Observer observer) {
        if (observer == null) {
            throw new NullPointerException("Observer passed is null");
        }

        synchronized (mutex) {
            if (!observers.contains(observer)) {
                observers.add(observer);
            }
        }
    }

    @Override
    public void unregister(Observer observer) {
        synchronized (mutex) {
            observers.remove(observer);
        }
    }

    @Override
    public void notifyObservers() {
        List<Observer> observersToBeNotified;

        synchronized (mutex) {
            if (!changed) {
                return;
            }

            observersToBeNotified = new ArrayList<>(this.observers);
            this.changed = false;
        }

        for (Observer observer: observersToBeNotified) {
            observer.update();
        }
    }

    public String getUpdate() {
        return this.message;
    }

    public void postMessage(String msg) {
        System.out.println("New message: " + msg);
        this.message = msg;
        this.changed = true;
        notifyObservers();
    }
}
