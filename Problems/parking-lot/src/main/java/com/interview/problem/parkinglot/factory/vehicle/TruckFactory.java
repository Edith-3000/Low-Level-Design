package com.interview.problem.parkinglot.factory.vehicle;

import com.interview.problem.parkinglot.enums.VehicleType;
import com.interview.problem.parkinglot.model.vehicle.Truck;
import com.interview.problem.parkinglot.model.vehicle.Vehicle;
import lombok.NonNull;

public class TruckFactory extends VehicleFactory {
    @Override
    public Vehicle createVehicle(@NonNull final String licensePlate) {
        return new Truck(licensePlate, VehicleType.TRUCK);
    }
}
