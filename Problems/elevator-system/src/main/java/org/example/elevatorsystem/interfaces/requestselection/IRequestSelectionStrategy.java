package org.example.elevatorsystem.interfaces.requestselection;

import lombok.NonNull;
import org.example.elevatorsystem.models.ElevatorCar;
import org.example.elevatorsystem.models.Request;

public interface IRequestSelectionStrategy {
    Request selectRequest(@NonNull final ElevatorCar elevatorCar);
}
