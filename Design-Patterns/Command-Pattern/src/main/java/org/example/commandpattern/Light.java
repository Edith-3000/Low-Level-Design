package org.example.commandpattern;

public class Light implements Receiver {
    @Override
    public void on() {
        System.out.println("Turn on light");
    }

    @Override
    public void off() {
        System.out.println("Turn off light");
    }

    @Override
    public void up() {
        System.out.println("Increase brightness");
    }

    @Override
    public void down() {
        System.out.println("Decrease brightness");
    }
}
