package org.example.pubsubsystem.models;

import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Topic {
    @Getter
    private final String id;
    @Getter
    private final String name;
    private final List<Message> messages;
    private final List<TopicSubscriber> subscribers;

    public Topic(@NonNull final String id, @NonNull final String name) {
        this.id = id;
        this.name = name;
        this.messages = new ArrayList<>();
        this.subscribers = new ArrayList<>();
    }

    public List<Message> getMessages() {
        return Collections.unmodifiableList(messages);
    }

    public List<TopicSubscriber> getSubscribers() {
        return Collections.unmodifiableList(subscribers);
    }

    public synchronized void addMessage(Message message) {
        messages.add(message);
    }

    public synchronized void addSubscriber(TopicSubscriber topicSubscriber) {
        subscribers.add(topicSubscriber);
    }

    public synchronized void removeSubscriber(TopicSubscriber topicSubscriber) {
        subscribers.remove(topicSubscriber);
    }
}
