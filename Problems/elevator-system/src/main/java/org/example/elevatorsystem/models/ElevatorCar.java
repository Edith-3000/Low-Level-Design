package org.example.elevatorsystem.models;

import lombok.Getter;
import org.example.elevatorsystem.enums.Direction;

import java.util.ArrayList;
import java.util.List;

public class ElevatorCar {
    private final int id;
    @Getter
    private int currentFloor;
    @Getter
    private Direction currentDirection;
    private List<Request> requests;

    public ElevatorCar(final int id) {
        this.id = id;
        this.currentFloor = 0;
        this.currentDirection = Direction.IDLE;
        requests = new ArrayList<>();
    }

    public void addRequest
}
