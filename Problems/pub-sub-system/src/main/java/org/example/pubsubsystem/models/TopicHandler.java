package org.example.pubsubsystem.models;

import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

public class TopicHandler {
    private final Topic topic;
    private final Map<String, SubscriberWorker> subscriberWorkers;

    public TopicHandler(@NonNull final Topic topic) {
        this.topic = topic;
        this.subscriberWorkers = new HashMap<>();
    }

    public void publish() {
        for (TopicSubscriber topicSubscriber: topic.getSubscribers()) {
            startSubscriberWorker(topicSubscriber);
        }
    }

    public void startSubscriberWorker(@NonNull final TopicSubscriber topicSubscriber) {
        synchronized (subscriberWorkers) {
            final String subscriberId = topicSubscriber.getSubscriber().getId();

            if (!subscriberWorkers.containsKey(subscriberId)) {
                final SubscriberWorker subscriberWorker = new SubscriberWorker(topic, topicSubscriber);
                subscriberWorkers.put(subscriberId, subscriberWorker);
                new Thread(subscriberWorker).start();
            }

            final SubscriberWorker subscriberWorker = subscriberWorkers.get(subscriberId);
            subscriberWorker.wakeUpIfNeeded();
        }
    }

    public void stopSubscriberWorker(@NonNull final TopicSubscriber topicSubscriber) {
        synchronized (subscriberWorkers) {
            final String subscriberId = topicSubscriber.getSubscriber().getId();

            if (subscriberWorkers.containsKey(subscriberId)) {
                final SubscriberWorker subscriberWorker = subscriberWorkers.get(subscriberId);
                subscriberWorker.stop();
                subscriberWorkers.remove(subscriberId);
            }
        }
    }
}
