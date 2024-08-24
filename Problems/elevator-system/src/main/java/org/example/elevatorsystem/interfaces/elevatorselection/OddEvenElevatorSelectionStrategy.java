package org.example.elevatorsystem.interfaces.elevatorselection;

import lombok.NonNull;
import org.example.elevatorsystem.enums.Direction;
import org.example.elevatorsystem.exceptions.NotFoundException;
import org.example.elevatorsystem.models.ElevatorCar;
import org.example.elevatorsystem.models.Request;

import java.util.List;

public class OddEvenElevatorSelectionStrategy implements IElevatorSelectionStrategy {
    @Override
    public ElevatorCar selectElevator(@NonNull final List<ElevatorCar> elevatorCars, @NonNull final Request request) throws NotFoundException {
        for (ElevatorCar elevatorCar: elevatorCars) {
            if ((elevatorCar.getId() % 2) == (request.getSourceFloorId() % 2)) {
                final int elevatorCarCurrentFloor = elevatorCar.getCurrentFloor();
                final Direction elevatorCarCurrentDirection = elevatorCar.getCurrentDirection();

                if ((request.getSourceFloorId() > elevatorCarCurrentFloor) && (elevatorCarCurrentDirection == Direction.UP)) {
                    return elevatorCar;
                } else if ((request.getSourceFloorId() < elevatorCarCurrentFloor) && (elevatorCarCurrentDirection == Direction.DOWN)) {
                    return elevatorCar;
                } else if (elevatorCarCurrentDirection == Direction.IDLE) {
                    return elevatorCar;
                }
            }
        }

        throw new NotFoundException("No elevator available to serve: " + request.getDirection() + " request from floor: " + request.getSourceFloorId());
    }
}
