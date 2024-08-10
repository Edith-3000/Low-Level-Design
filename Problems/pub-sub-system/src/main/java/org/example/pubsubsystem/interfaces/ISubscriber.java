package org.example.pubsubsystem.interfaces;

import org.example.pubsubsystem.models.Message;

public interface ISubscriber {
    String getId();
    void consume(Message message) throws InterruptedException;
}
