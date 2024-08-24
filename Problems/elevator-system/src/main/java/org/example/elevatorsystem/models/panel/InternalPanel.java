package org.example.elevatorsystem.models.panel;

import lombok.NonNull;
import org.example.elevatorsystem.models.ElevatorCar;
import org.example.elevatorsystem.models.Request;
import org.example.elevatorsystem.models.requestdispatcher.InternalRequestDispatcher;

public class InternalPanel {
    private final ElevatorCar elevatorCar;

    public InternalPanel(@NonNull final ElevatorCar elevatorCar) {
        this.elevatorCar = elevatorCar;
    }

    public void pressButton(int floor) {
        InternalRequestDispatcher.getInstance().dispatchRequest(new Request(elevatorCar.getCurrentFloor(), floor), elevatorCar);
    }
}
