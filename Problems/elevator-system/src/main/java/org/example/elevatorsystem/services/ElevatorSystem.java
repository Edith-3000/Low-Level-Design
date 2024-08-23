package org.example.elevatorsystem.services;

import lombok.Getter;
import lombok.NonNull;
import org.example.elevatorsystem.interfaces.elevatorselection.IElevatorSelectionStrategy;
import org.example.elevatorsystem.models.ElevatorCarHandler;

import java.util.Collections;
import java.util.List;

public class ElevatorSystem {
    private static volatile ElevatorSystem instance;
    @Getter
    private IElevatorSelectionStrategy elevatorSelectionStrategy;
    private List<ElevatorCarHandler> elevatorCarHandlers;

    private ElevatorSystem() {}

    public static ElevatorSystem getInstance() {
        if (instance == null) {
            synchronized (ElevatorSystem.class) {
                if (instance == null) {
                    instance = new ElevatorSystem();
                }
            }
        }

        return instance;
    }

    public void setElevatorSelectionStrategy(@NonNull final IElevatorSelectionStrategy elevatorSelectionStrategy) {
        this.elevatorSelectionStrategy = elevatorSelectionStrategy;
    }

    public List<ElevatorCarHandler> getElevatorCarHandlers() {
        return Collections.unmodifiableList(elevatorCarHandlers);
    }
}
