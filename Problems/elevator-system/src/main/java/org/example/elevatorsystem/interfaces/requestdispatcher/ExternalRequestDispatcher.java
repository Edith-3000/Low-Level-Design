package org.example.elevatorsystem.interfaces.requestdispatcher;

import lombok.NonNull;
import org.example.elevatorsystem.interfaces.elevatorselection.IElevatorSelectionStrategy;
import org.example.elevatorsystem.models.ElevatorCarHandler;
import org.example.elevatorsystem.models.Request;
import org.example.elevatorsystem.services.ElevatorSystem;

import java.util.List;

public class ExternalRequestDispatcher implements IRequestDispatcher {
    private volatile ExternalRequestDispatcher instance;

    private ExternalRequestDispatcher() {}

    @Override
    public void dispatchRequest(@NonNull final Request request) {
        @NonNull final IElevatorSelectionStrategy elevatorSelectionStrategy = ElevatorSystem.getInstance().getElevatorSelectionStrategy();
        @NonNull final List<ElevatorCarHandler> elevatorCarHandlers = ElevatorSystem.getInstance().getElevatorCarHandlers();

        @NonNull final ElevatorCarHandler elevatorCarHandler = elevatorSelectionStrategy.selectElevator(elevatorCarHandlers, request);

        elevatorCarHandler.
    }
}
