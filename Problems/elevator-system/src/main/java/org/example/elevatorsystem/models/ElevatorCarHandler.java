package org.example.elevatorsystem.models;

import lombok.Getter;

public class ElevatorCarHandler {
    @Getter
    private final ElevatorCar elevatorCar;

    public ElevatorCarHandler(ElevatorCar elevatorCar) {
        this.elevatorCar = elevatorCar;
    }

    public void acceptRequest(Request request) {

    }
}
