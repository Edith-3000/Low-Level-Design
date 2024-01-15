package org.example.observerpattern;

public class NormalObserver implements Observer {
    private final MyTopic myTopicSubject;
    private String message;

    public NormalObserver(MyTopic subject) {
        this.myTopicSubject = subject;
        this.message = "";
    }

    @Override
    public void update() {
        this.message = this.myTopicSubject.getUpdate();
        System.out.println("Updated message in NormalObserver: " + this.message);
    }
}
