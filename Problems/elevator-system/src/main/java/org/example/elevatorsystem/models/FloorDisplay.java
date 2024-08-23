package org.example.elevatorsystem.models;

import lombok.NonNull;
import org.example.elevatorsystem.enums.Direction;

public class FloorDisplay {
    public void display(int floorId, @NonNull Direction direction) {
        System.out.println("Elevator requested for: " + direction + " at floor: " + floorId);
    }
}
