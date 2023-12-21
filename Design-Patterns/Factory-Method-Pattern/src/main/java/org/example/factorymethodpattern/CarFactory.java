package org.example.factorymethodpattern;

public class CarFactory implements ToyFactory {
    @Override
    public Toy createToy() {
        return new Car();
    }
}
