package org.example.pubsubsystem.models;

import lombok.Getter;
import lombok.NonNull;
import org.example.pubsubsystem.interfaces.ISubscriber;

import java.util.concurrent.atomic.AtomicInteger;

public class TopicSubscriber {
    private final AtomicInteger offset;
    @Getter
    private final ISubscriber subscriber;

    public TopicSubscriber(@NonNull final ISubscriber subscriber) {
        this.subscriber = subscriber;
        this.offset = new AtomicInteger(0);
    }

    public int getOffset() {
        return offset.get();
    }

    public void setOffset(int newOffset) {
        offset.compareAndSet(newOffset - 1, newOffset);
    }

    public void resetOffset(int newOffset) {
        offset.set(newOffset);
    }
}
