package org.example.elevatorsystem.services;

import lombok.Getter;
import lombok.NonNull;
import org.example.elevatorsystem.enums.Direction;
import org.example.elevatorsystem.interfaces.elevatorselection.IElevatorSelectionStrategy;
import org.example.elevatorsystem.interfaces.requestselection.IRequestSelectionStrategy;
import org.example.elevatorsystem.models.ElevatorCar;
import org.example.elevatorsystem.models.Floor;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ElevatorSystem {
    private static volatile ElevatorSystem instance;
    @Getter
    private IElevatorSelectionStrategy elevatorSelectionStrategy;
    @Getter
    private IRequestSelectionStrategy requestSelectionStrategy;
    private final List<ElevatorCar> elevatorCars;
    private final List<Floor> floors;

    private ElevatorSystem() {
        elevatorCars = new CopyOnWriteArrayList<>();
        floors = new CopyOnWriteArrayList<>();
    }

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

    public void setRequestSelectionStrategy(@NonNull final IRequestSelectionStrategy requestSelectionStrategy) {
        this.requestSelectionStrategy = requestSelectionStrategy;
    }

    public List<ElevatorCar> getElevatorCars() {
        return Collections.unmodifiableList(elevatorCars);
    }

    public void addFloor(@NonNull final Floor floor) {
        floors.add(floor);
    }

    public void addElevatorCar(@NonNull final ElevatorCar elevatorCar) {
        elevatorCars.add(elevatorCar);
        new Thread(elevatorCar::run).start();
    }

    public int requestElevatorCar(final int floorId, Direction direction) {
        int elevatorCarId = -1;

        for (Floor floor: floors) {
            if (floor.getId() == floorId) {
                elevatorCarId = floor.getExternalPanel().pressButton(direction);
                break;
            }
        }

        return elevatorCarId;
    }

    public void selectFloorInElevatorCar(final int dstFloorId, final int elevatorCarId) {
        for (ElevatorCar elevatorCar: elevatorCars) {
            if (elevatorCar.getId() == elevatorCarId) {
                elevatorCar.getInternalPanel().pressButton(dstFloorId);
                return;
            }
        }
    }
}
