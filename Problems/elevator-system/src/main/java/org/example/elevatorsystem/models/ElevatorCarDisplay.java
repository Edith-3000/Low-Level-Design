package org.example.elevatorsystem.models;

import lombok.NonNull;
import org.example.elevatorsystem.enums.Direction;

public class ElevatorCarDisplay {
    public void display(final int elevatorCarId, final int floorId, @NonNull final Direction direction) {
        System.out.println("Elevator: " + elevatorCarId + " at floor: " + floorId + " & moving " + direction);
    }
}
