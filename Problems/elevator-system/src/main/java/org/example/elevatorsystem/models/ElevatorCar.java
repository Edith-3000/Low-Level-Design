package org.example.elevatorsystem.models;

import lombok.Getter;
import org.example.elevatorsystem.enums.Direction;
import org.example.elevatorsystem.models.display.ElevatorCarDisplay;
import org.example.elevatorsystem.models.panel.InternalPanel;
import org.example.elevatorsystem.services.ElevatorSystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ElevatorCar {
    @Getter
    private final int id;
    @Getter
    private int currentFloor;
    @Getter
    private Direction currentDirection;
    private final List<Request> requests;
    @Getter
    private final InternalPanel internalPanel;
    private final ElevatorCarDisplay elevatorCarDisplay;

    public ElevatorCar(final int id) {
        this.id = id;
        this.currentFloor = 0;
        this.currentDirection = Direction.IDLE;
        this.requests = new ArrayList<>();
        this.internalPanel = new InternalPanel(this);
        this.elevatorCarDisplay = new ElevatorCarDisplay();
    }

    public void addRequest(Request request) {
        synchronized (requests) {
            requests.add(request);
            requests.notify();
        }
    }

    public List<Request> getRequests() {
        return Collections.unmodifiableList(requests);
    }

    private void processRequests() {
        synchronized (requests) {
            while (true) {
                while (!requests.isEmpty()) {
                    Request requestToProcess = ElevatorSystem.getInstance().getRequestSelectionStrategy().selectRequest(this);
                    processRequest(requestToProcess);
                    requests.remove(requestToProcess);
                }

                currentDirection = Direction.IDLE;

                try {
                    requests.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void processRequest(Request request) {
        int startFloor = currentFloor;
        int endFloor = (request.getDestinationFloorId() == Integer.MIN_VALUE) ? request.getSourceFloorId() : request.getDestinationFloorId();

        if (startFloor < endFloor) {
            elevatorCarDisplay.display(id, currentFloor, currentDirection);
            currentDirection = Direction.UP;

            for (int i = startFloor + 1; i <= endFloor; i++) {
                currentFloor = i;

                if (i == endFloor) {
                    currentDirection = Direction.IDLE;
                }

                elevatorCarDisplay.display(id, currentFloor, currentDirection);

                try {
                    Thread.sleep(1000); // Simulating elevator movement
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        } else if (startFloor > endFloor) {
            elevatorCarDisplay.display(id, currentFloor, currentDirection);
            currentDirection = Direction.DOWN;

            for (int i = startFloor - 1; i >= endFloor; i--) {
                currentFloor = i;

                if (i == endFloor) {
                    currentDirection = Direction.IDLE;
                }

                elevatorCarDisplay.display(id, currentFloor, currentDirection);

                try {
                    Thread.sleep(1000); // Simulating elevator movement
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void run() {
        processRequests();
    }
}
