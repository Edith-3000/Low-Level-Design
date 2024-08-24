package org.example.elevatorsystem.models.requestdispatcher;

import lombok.NonNull;
import org.example.elevatorsystem.models.ElevatorCar;
import org.example.elevatorsystem.models.Request;
import org.jetbrains.annotations.NotNull;

public class InternalRequestDispatcher {
    private static volatile InternalRequestDispatcher instance;

    private InternalRequestDispatcher() {}

    public static InternalRequestDispatcher getInstance() {
        if (instance == null) {
            synchronized (InternalRequestDispatcher.class) {
                if (instance == null) {
                    instance = new InternalRequestDispatcher();
                }
            }
        }

        return instance;
    }

    public void dispatchRequest(@NotNull final Request request, @NonNull final ElevatorCar elevatorCar) {
        elevatorCar.addRequest(request);
    }
}
