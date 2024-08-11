package org.example.pubsubsystem.models;

import lombok.NonNull;

public class SubscriberWorker implements Runnable {
    private final Topic topic;
    private final TopicSubscriber topicSubscriber;
    private volatile boolean running = true;

    public SubscriberWorker(@NonNull final Topic topic, @NonNull final TopicSubscriber topicSubscriber) {
        this.topic = topic;
        this.topicSubscriber = topicSubscriber;
    }

    @Override
    public void run() {
        while (running && !Thread.currentThread().isInterrupted()) {
            synchronized (topicSubscriber) {
                // Re-fetch the current offset in case it was reset while waiting, otherwise code will
                // never go out of this for loop
                while (topicSubscriber.getOffset() >= topic.getMessages().size()) {
                    try {
                        topicSubscriber.wait();
                    } catch (InterruptedException e) {
                        if (!running || Thread.currentThread().isInterrupted()) {
                            // Exit if the thread is interrupted or no longer running
                            return;
                        } else {
                            throw new RuntimeException(e);
                        }
                    }
                }

                // Re-fetch the current offset in case it was reset
                int currentOffset = topicSubscriber.getOffset();

                if (running && !Thread.currentThread().isInterrupted()) {
                    Message message = topic.getMessages().get(currentOffset);
                    try {
                        topicSubscriber.getSubscriber().consume(message);
                    } catch (InterruptedException e) {
                        if (!running || Thread.currentThread().isInterrupted()) {
                            // Exit if the thread is interrupted or no longer running
                            return;
                        } else {
                            throw new RuntimeException(e);
                        }
                    }

                    topicSubscriber.setOffset(currentOffset + 1);
                }
            }
        }
    }

    public void wakeUpIfNeeded() {
        synchronized (topicSubscriber) {
            topicSubscriber.notify();
        }
    }

    public void stop() {
        running = false;
        Thread.currentThread().interrupt();
    }
}
