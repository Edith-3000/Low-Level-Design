package org.example.elevatorsystem.interfaces.elevatorselection;

import lombok.NonNull;
import org.example.elevatorsystem.exceptions.NotFoundException;
import org.example.elevatorsystem.models.ElevatorCar;
import org.example.elevatorsystem.models.Request;

import java.util.List;

public interface IElevatorSelectionStrategy {
    ElevatorCar selectElevator(@NonNull final List<ElevatorCar> elevatorCars, @NonNull final Request request) throws NotFoundException;
}
