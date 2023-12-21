package org.example.abstractfactorypattern;

public class VictorianSofa implements Sofa {
    @Override
    public void sit() {
        System.out.println("Sitting on a Victorian-era sofa!");
    }
}
