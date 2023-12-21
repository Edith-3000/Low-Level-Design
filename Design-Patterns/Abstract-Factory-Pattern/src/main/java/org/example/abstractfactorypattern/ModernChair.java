package org.example.abstractfactorypattern;

public class ModernChair implements Chair {
    @Override
    public void sit() {
        System.out.println("Sitting on a Modern-era chair!");
    }
}
