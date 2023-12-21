package org.example.factorymethodpattern;

public class Client {
    public static void main(String[] args) {
        ToyFactoryRegistry.registerFactory("Car", new CarFactory());
        ToyFactoryRegistry.registerFactory("Doll", new DollFactory());

        String userInput = "Car";

        Toy toy = ToyFactoryRegistry.createToy(userInput);
        toy.play();
    }
}
