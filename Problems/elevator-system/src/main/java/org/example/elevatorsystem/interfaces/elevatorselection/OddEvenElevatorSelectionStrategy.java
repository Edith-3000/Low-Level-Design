package org.example.elevatorsystem.interfaces.elevatorselection;

import lombok.NonNull;
import org.example.elevatorsystem.enums.Direction;
import org.example.elevatorsystem.exceptions.NotFoundException;
import org.example.elevatorsystem.models.ElevatorCarHandler;
import org.example.elevatorsystem.models.Request;

import java.util.List;

public class OddEvenElevatorSelectionStrategy implements IElevatorSelectionStrategy {
    @Override
    public ElevatorCarHandler selectElevator(@NonNull final List<ElevatorCarHandler> elevatorCarHandlers, @NonNull final Request request) throws NotFoundException {
        for (ElevatorCarHandler elevatorCarHandler: elevatorCarHandlers) {
            final int elevatorCarCurrentFloor = elevatorCarHandler.getElevatorCar().getCurrentFloor();
            final Direction elevatorCarCurrentDirection = elevatorCarHandler.getElevatorCar().getCurrentDirection();

            if ((elevatorCarCurrentFloor % 2) == (request.getSourceFloorId() % 2)) {
                if ((request.getSourceFloorId() > elevatorCarCurrentFloor) && (elevatorCarCurrentDirection == Direction.UP)) {
                    return elevatorCarHandler;
                } else if ((request.getSourceFloorId() < elevatorCarCurrentFloor) && (elevatorCarCurrentDirection == Direction.DOWN)) {
                    return elevatorCarHandler;
                } else if (elevatorCarCurrentDirection == Direction.IDLE) {
                    return elevatorCarHandler;
                }
            }
        }

        throw new NotFoundException("No elevator available to serve: " + request.getDirection() + " request from floor: " + request.getSourceFloorId());
    }
}
