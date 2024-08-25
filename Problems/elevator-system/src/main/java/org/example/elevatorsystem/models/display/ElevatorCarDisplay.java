package org.example.elevatorsystem.models.display;

import lombok.NonNull;
import org.example.elevatorsystem.enums.Direction;

public class ElevatorCarDisplay {
    public void display(final int elevatorCarId, final int floorId, @NonNull final Direction direction) {
        String moveStr = "";
        if (direction == Direction.IDLE) {
            moveStr = " & " + direction;
        } else {
            moveStr = " & moving " + direction;
        }

        System.out.println("Elevator: " + elevatorCarId + " at floor: " + floorId + moveStr);
    }
}
