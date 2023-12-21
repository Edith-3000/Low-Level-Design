package org.example.abstractfactorypattern;

public class VictorianChair implements Chair {
    @Override
    public void sit() {
        System.out.println("Sitting on a Victorian-era chair!");
    }
}
