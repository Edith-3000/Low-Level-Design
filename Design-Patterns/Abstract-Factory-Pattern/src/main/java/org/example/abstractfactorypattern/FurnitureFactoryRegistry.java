package org.example.abstractfactorypattern;

import java.util.HashMap;
import java.util.Map;

public class FurnitureFactoryRegistry {
    private static final Map<String, FurnitureFactory> factoryMap = new HashMap<>();

    public static void registerFactory(String eraType, FurnitureFactory furnitureFactory) {
        factoryMap.put(eraType, furnitureFactory);
    }

    public static Chair createChair(String eraType) {
        FurnitureFactory furnitureFactory = factoryMap.get(eraType);

        if (furnitureFactory != null) {
            return furnitureFactory.createChair();
        } else {
            throw new IllegalArgumentException("Unknown era type: " + eraType);
        }
    }

    public static Sofa createSofa(String eraType) {
        FurnitureFactory furnitureFactory = factoryMap.get(eraType);

        if (furnitureFactory != null) {
            return furnitureFactory.createSofa();
        } else {
            throw new IllegalArgumentException("Unknown era type: " + eraType);
        }
    }
}
