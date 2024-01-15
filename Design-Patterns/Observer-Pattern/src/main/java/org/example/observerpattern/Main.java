package org.example.observerpattern;

public class Main {
    public static void main(String[] args) {
        MyTopic myTopicSubject = new MyTopic();

        Observer observer1 = new NormalObserver(myTopicSubject);
        Observer observer2 = new NormalObserver(myTopicSubject);
        Observer observer3 = new NormalObserver(myTopicSubject);

        myTopicSubject.register(observer1);
        myTopicSubject.register(observer2);
        myTopicSubject.register(observer3);

        myTopicSubject.postMessage("HELLO_WORLD_!");

        myTopicSubject.unregister(observer2);

        myTopicSubject.postMessage("HOW_ARE_YOU_?");

        myTopicSubject.notifyObservers();
    }
}
