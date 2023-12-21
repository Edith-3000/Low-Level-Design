package org.example.abstractfactorypattern;

public class Client {
    public static void main(String[] args) {
        FurnitureFactoryRegistry.registerFactory("Victorian", new VictorianFurnitureFactory());
        FurnitureFactoryRegistry.registerFactory("Modern", new ModernFurnitureFactory());

        String userInput = "Modern";

        Chair chair = FurnitureFactoryRegistry.createChair(userInput);
        Sofa sofa = FurnitureFactoryRegistry.createSofa(userInput);

        chair.sit();
        sofa.sit();
    }
}
