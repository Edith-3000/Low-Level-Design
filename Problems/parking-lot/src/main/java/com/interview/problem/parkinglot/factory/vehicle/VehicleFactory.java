package com.interview.problem.parkinglot.factory.vehicle;

import com.interview.problem.parkinglot.model.vehicle.Vehicle;
import lombok.NonNull;

public abstract class VehicleFactory {
    public abstract Vehicle createVehicle(@NonNull final String licensePlate);
}
