package com.interview.problem.parkinglot.factory.vehicle;

import com.interview.problem.parkinglot.enums.VehicleType;
import com.interview.problem.parkinglot.model.vehicle.Bike;
import com.interview.problem.parkinglot.model.vehicle.Vehicle;
import lombok.NonNull;

public class BikeFactory extends VehicleFactory {
    @Override
    public Vehicle createVehicle(@NonNull final String licensePlate) {
        return new Bike(licensePlate, VehicleType.BIKE);
    }
}
