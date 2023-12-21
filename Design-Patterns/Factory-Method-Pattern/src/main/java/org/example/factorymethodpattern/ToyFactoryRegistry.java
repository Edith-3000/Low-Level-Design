package org.example.factorymethodpattern;

import java.util.HashMap;
import java.util.Map;

public class ToyFactoryRegistry {
    private static final Map<String, ToyFactory> factoryMap = new HashMap<>();

    public static void registerFactory(String toyType, ToyFactory toyFactory) {
        factoryMap.put(toyType, toyFactory);
    }

    public static Toy createToy(String toyType) {
        ToyFactory toyFactory = factoryMap.get(toyType);

        if (toyFactory != null) {
            return toyFactory.createToy();
        } else {
            throw new IllegalArgumentException("Unknown toy type: " + toyType);
        }
    }
}
