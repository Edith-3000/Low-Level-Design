package org.example.factorymethodpattern;

public class Car implements Toy {
    @Override
    public void play() {
        System.out.println("Playing with a toy CAR!");
    }
}
