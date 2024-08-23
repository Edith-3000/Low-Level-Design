package org.example.elevatorsystem.interfaces.elevatorselection;

import lombok.NonNull;
import org.example.elevatorsystem.enums.Direction;
import org.example.elevatorsystem.exceptions.NotFoundException;
import org.example.elevatorsystem.models.ElevatorCarHandler;
import org.example.elevatorsystem.models.Request;

import java.util.List;

public class MinDistanceElevatorSelectionStrategy implements IElevatorSelectionStrategy {
    @Override
    public ElevatorCarHandler selectElevator(@NonNull final List<ElevatorCarHandler> elevatorCarHandlers, @NonNull final Request request) throws NotFoundException {
        int minDistance = Integer.MAX_VALUE;
        ElevatorCarHandler minDistElevatorCarHandler = null;

        for (ElevatorCarHandler elevatorCarHandler: elevatorCarHandlers) {
            final int elevatorCarCurrentFloor = elevatorCarHandler.getElevatorCar().getCurrentFloor();

            if (Math.abs(request.getSourceFloorId() - elevatorCarCurrentFloor) < minDistance) {
                minDistance = Math.abs(request.getSourceFloorId() - elevatorCarCurrentFloor);
                minDistElevatorCarHandler = elevatorCarHandler;
            }
        }

        if (minDistElevatorCarHandler == null) {
            throw new NotFoundException("No elevator available to serve: " + request.getDirection() + " request from floor: " + request.getSourceFloorId());
        }

        return minDistElevatorCarHandler;
    }
}
