package org.example.pubsubsystem.services;

import lombok.NonNull;
import org.example.pubsubsystem.interfaces.ISubscriber;
import org.example.pubsubsystem.models.Message;
import org.example.pubsubsystem.models.Topic;
import org.example.pubsubsystem.models.TopicHandler;
import org.example.pubsubsystem.models.TopicSubscriber;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PubSub {
    private static volatile PubSub instance;
    private final Map<String, TopicHandler> topicHandlers;

    private PubSub() {
        topicHandlers = new HashMap<>(); // or a ConcurrentHashMap<>() should be used?
    }

    public static PubSub getInstance() {
        if (instance == null) {
            synchronized (PubSub.class) {
                if (instance == null) {
                    instance = new PubSub();
                }
            }
        }

        return instance;
    }

    public Topic createTopic(@NonNull final String topicName) {
        final Topic topic = new Topic(UUID.randomUUID().toString(), topicName);
        TopicHandler topicHandler = new TopicHandler(topic);
        topicHandlers.put(topic.getId(), topicHandler);
        System.out.println("Created topic: " + topicName + " with ID: " + topic.getId());
        return topic;
    }

    public void subscribe(@NonNull final ISubscriber subscriber, @NonNull final Topic topic) {
        topic.addSubscriber(new TopicSubscriber(subscriber));
        System.out.println(subscriber.getId() + " subscribed to topic: " + topic.getName() + " [ID: " + topic.getId() + "]");
    }

    public void unsubscribe(@NonNull final ISubscriber subscriber, @NonNull final Topic topic) {
        for (TopicSubscriber topicSubscriber: topic.getSubscribers()) {
            if (topicSubscriber.getSubscriber().equals(subscriber)) {
                TopicHandler topicHandler = topicHandlers.get(topic.getId());
                topicHandler.stopSubscriberWorker(topicSubscriber);
                topic.removeSubscriber(topicSubscriber);
                System.out.println(topicSubscriber.getSubscriber().getId() + " unsubscribed from topic " + topic.getName() + " [ID: " + topic.getId() + "]");
            }
        }
    }

    public void publish(@NonNull final Topic topic, @NonNull final Message message) {
        topic.addMessage(message);
        System.out.println("[" + message.getId() + "] published to topic: " + topic.getName() + " [ID: " + topic.getId() + "]");
        new Thread(() -> topicHandlers.get(topic.getId()).publish()).start();
    }

    public void resetOffset(@NonNull final Topic topic, @NonNull final ISubscriber subscriber, final int newOffset) {
        for (TopicSubscriber topicSubscriber: topic.getSubscribers()) {
            if (topicSubscriber.getSubscriber().equals(subscriber)) {
                topicSubscriber.resetOffset(newOffset);
                System.out.println(topicSubscriber.getSubscriber().getId() + " offset reset to " + newOffset);
                new Thread(() -> topicHandlers.get(topic.getId()).startSubscriberWorker(topicSubscriber)).start();
                break;
            }
        }
    }
}
