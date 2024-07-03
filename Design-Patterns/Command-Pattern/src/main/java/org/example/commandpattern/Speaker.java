package org.example.commandpattern;

public class Speaker implements Receiver {
    @Override
    public void on() {
        System.out.println("Turn on speaker");
    }

    @Override
    public void off() {
        System.out.println("Turn off speaker");
    }

    @Override
    public void up() {
        System.out.println("Increase volume");
    }

    @Override
    public void down() {
        System.out.println("Decrease volume");
    }
}
