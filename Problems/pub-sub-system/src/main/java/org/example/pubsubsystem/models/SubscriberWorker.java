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
                int currentOffset = topicSubscriber.getOffset();

                while (currentOffset >= topic.getMessages().size()) {
                    try {
                     topicSubscriber.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

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
