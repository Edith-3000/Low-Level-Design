package org.example.pubsubsystem.models;

import lombok.NonNull;

public class SubscriberWorker implements Runnable {
    private final Topic topic;
    private final TopicSubscriber topicSubscriber;

    public SubscriberWorker(@NonNull final Topic topic, @NonNull final TopicSubscriber topicSubscriber) {
        this.topic = topic;
        this.topicSubscriber = topicSubscriber;
    }

    @Override
    public void run() {
        synchronized (topicSubscriber) {
            do {
                // Re-fetch the current offset in case it was reset while waiting, otherwise code will
                // never go out of this for loop
                while (topicSubscriber.getOffset() >= topic.getMessages().size()) {
                    try {
                     topicSubscriber.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                // Re-fetch the current offset in case it was reset
                int currentOffset = topicSubscriber.getOffset();

                Message message = topic.getMessages().get(currentOffset);
                try {
                    topicSubscriber.getSubscriber().consume(message);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                topicSubscriber.setOffset(currentOffset + 1);
            } while (true);
        }
    }

    public void wakeUpIfNeeded() {
        synchronized (topicSubscriber) {
            topicSubscriber.notify();
        }
    }
}
