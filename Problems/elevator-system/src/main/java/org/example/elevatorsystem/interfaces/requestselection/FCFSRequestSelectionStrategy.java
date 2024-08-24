package org.example.elevatorsystem.interfaces.requestselection;

import lombok.NonNull;
import org.example.elevatorsystem.models.ElevatorCar;
import org.example.elevatorsystem.models.Request;

import java.util.List;
import java.util.Objects;

public class FCFSRequestSelectionStrategy implements IRequestSelectionStrategy {
    @Override
    public Request selectRequest(@NonNull final ElevatorCar elevatorCar) {
        List<Request> request = Objects.requireNonNull(elevatorCar.getRequests());
        return request.get(0);
    }
}
