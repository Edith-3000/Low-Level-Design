package org.example.elevatorsystem.models.requestdispatcher;

import lombok.NonNull;
import org.example.elevatorsystem.interfaces.elevatorselection.IElevatorSelectionStrategy;
import org.example.elevatorsystem.models.ElevatorCar;
import org.example.elevatorsystem.models.Request;
import org.example.elevatorsystem.services.ElevatorSystem;

import java.util.List;

public class ExternalRequestDispatcher {
    private static volatile ExternalRequestDispatcher instance;

    private ExternalRequestDispatcher() {}

    public static ExternalRequestDispatcher getInstance() {
        if (instance == null) {
            synchronized (ExternalRequestDispatcher.class) {
                if (instance == null) {
                    instance = new ExternalRequestDispatcher();
                }
            }
        }

        return instance;
    }

    public int dispatchRequest(@NonNull final Request request) {
        @NonNull final IElevatorSelectionStrategy elevatorSelectionStrategy = ElevatorSystem.getInstance().getElevatorSelectionStrategy();
        @NonNull final List<ElevatorCar> elevatorCars = ElevatorSystem.getInstance().getElevatorCars();

        @NonNull final ElevatorCar elevatorCar = elevatorSelectionStrategy.selectElevator(elevatorCars, request);

        elevatorCar.addRequest(request);

        return elevatorCar.getId();
    }
}
