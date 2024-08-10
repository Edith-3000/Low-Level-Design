package org.example.pubsubsystem.interfaces;

import lombok.AllArgsConstructor;
import org.example.pubsubsystem.models.Message;

@AllArgsConstructor
public class SleepingSubscriber implements ISubscriber {
    private final String id;
    private final long sleepTimeInMillis;

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void consume(Message message) throws InterruptedException {
        System.out.println("Subscriber: " + id + " started consuming message with ID: [" + message.getId() + "]");
        Thread.sleep(sleepTimeInMillis);
        System.out.println("Subscriber: " + id + " finished consuming message with ID: [" + message.getId() + "]");
    }
}
