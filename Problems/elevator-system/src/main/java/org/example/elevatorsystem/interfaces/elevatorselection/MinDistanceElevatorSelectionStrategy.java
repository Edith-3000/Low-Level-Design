package org.example.elevatorsystem.interfaces.elevatorselection;

import lombok.NonNull;
import org.example.elevatorsystem.exceptions.NotFoundException;
import org.example.elevatorsystem.models.ElevatorCar;
import org.example.elevatorsystem.models.Request;

import java.util.List;

public class MinDistanceElevatorSelectionStrategy implements IElevatorSelectionStrategy {
    @Override
    public ElevatorCar selectElevator(@NonNull final List<ElevatorCar> elevatorCars, @NonNull final Request request) throws NotFoundException {
        int minDistance = Integer.MAX_VALUE;
        ElevatorCar minDistElevatorCar = null;

        for (ElevatorCar elevatorCar: elevatorCars) {
            final int elevatorCarCurrentFloor = elevatorCar.getCurrentFloor();

            if (Math.abs(request.getSourceFloorId() - elevatorCarCurrentFloor) < minDistance) {
                minDistance = Math.abs(request.getSourceFloorId() - elevatorCarCurrentFloor);
                minDistElevatorCar = elevatorCar;
            }
        }

        if (minDistElevatorCar == null) {
            throw new NotFoundException("No elevator available to serve: " + request.getDirection() + " request from floor: " + request.getSourceFloorId());
        }

        return minDistElevatorCar;
    }
}
