package com.interview.problem.parkinglot.model.vehicle;

import com.interview.problem.parkinglot.enums.VehicleType;
import lombok.NonNull;

public class Car extends Vehicle {
    public Car(@NonNull final String licensePlate, @NonNull final VehicleType vehicleType) {
        super(licensePlate, vehicleType);
    }
}
