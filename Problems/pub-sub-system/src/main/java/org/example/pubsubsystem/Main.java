package org.example.pubsubsystem;

import org.example.pubsubsystem.interfaces.ISubscriber;
import org.example.pubsubsystem.interfaces.SleepingSubscriber;
import org.example.pubsubsystem.models.Message;
import org.example.pubsubsystem.models.Topic;
import org.example.pubsubsystem.services.PubSub;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        final PubSub pubsub = PubSub.getInstance();
        final Topic t1 = pubsub.createTopic("t1");
        final ISubscriber s1 = new SleepingSubscriber("s1", 1000);
        pubsub.subscribe(s1, t1);
        pubsub.publish(t1, new Message("m1", System.currentTimeMillis(), "This is message_1"));
        Thread.sleep(30000);
        pubsub.resetOffset(t1, s1, 0);

//        final PubSub pubsub = PubSub.getInstance();
//
//        final Topic t1 = pubsub.createTopic("t1");
//        final Topic t2 = pubsub.createTopic("t2");
//
//        final ISubscriber s1 = new SleepingSubscriber("s1", 10000);
//        final ISubscriber s2 = new SleepingSubscriber("s2", 3000);
//
//        pubsub.subscribe(s1, t1);
//        pubsub.subscribe(s2, t1);
//
//        final ISubscriber s3 = new SleepingSubscriber("s3", 7000);
//
//        pubsub.subscribe(s3, t2);
//
//        pubsub.publish(t1, new Message("m1", System.currentTimeMillis(), "This is message_1"));
//        pubsub.publish(t1, new Message("m2", System.currentTimeMillis(), "This is message_2"));
//
//        pubsub.publish(t2, new Message("m3", System.currentTimeMillis(), "This is message_3"));
//
//        Thread.sleep(15000);
//
//        pubsub.publish(t2, new Message("m4", System.currentTimeMillis(), "This is message_4"));
//        pubsub.publish(t1, new Message("m5", System.currentTimeMillis(), "This is message_5"));
//
//        Thread.sleep(30000);
//
//        pubsub.resetOffset(t1, s1, 0);
    }
}
