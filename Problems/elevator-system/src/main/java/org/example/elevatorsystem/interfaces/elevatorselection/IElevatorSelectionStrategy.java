package org.example.elevatorsystem.interfaces.elevatorselection;

import lombok.NonNull;
import org.example.elevatorsystem.exceptions.NotFoundException;
import org.example.elevatorsystem.models.ElevatorCarHandler;
import org.example.elevatorsystem.models.Request;

import java.util.List;

public interface IElevatorSelectionStrategy {
    ElevatorCarHandler selectElevator(@NonNull final List<ElevatorCarHandler> elevatorCarHandlers, @NonNull final Request request) throws NotFoundException;
}
