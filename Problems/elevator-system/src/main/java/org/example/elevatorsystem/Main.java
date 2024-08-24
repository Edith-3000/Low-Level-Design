package org.example.elevatorsystem;

import org.example.elevatorsystem.enums.Direction;
import org.example.elevatorsystem.interfaces.elevatorselection.OddEvenElevatorSelectionStrategy;
import org.example.elevatorsystem.interfaces.requestselection.FCFSRequestSelectionStrategy;
import org.example.elevatorsystem.models.ElevatorCar;
import org.example.elevatorsystem.models.Floor;
import org.example.elevatorsystem.services.ElevatorSystem;

public class Main {
    public static void main(String[] args) {
        ElevatorSystem elevatorSystem = ElevatorSystem.getInstance();

        elevatorSystem.setElevatorSelectionStrategy(new OddEvenElevatorSelectionStrategy());

        elevatorSystem.setRequestSelectionStrategy(new FCFSRequestSelectionStrategy());

        for (int i = 0; i < 20; i++) {
            Floor floor = new Floor(i);
            elevatorSystem.addFloor(floor);
        }

        for (int i = 0; i < 2; i++) {
            ElevatorCar elevatorCar = new ElevatorCar(i);
            elevatorSystem.addElevatorCar(elevatorCar);
        }

        int f1 = elevatorSystem.requestElevatorCar(0, Direction.UP);
        int f2 = elevatorSystem.requestElevatorCar(5, Direction.DOWN);

        elevatorSystem.selectFloorInElevatorCar(11, f1);
        elevatorSystem.selectFloorInElevatorCar(1, f1);
        elevatorSystem.selectFloorInElevatorCar(8, f2);
    }
}
