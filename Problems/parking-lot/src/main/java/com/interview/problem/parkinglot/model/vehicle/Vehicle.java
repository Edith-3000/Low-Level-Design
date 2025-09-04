package com.interview.problem.parkinglot.model.vehicle;

import com.interview.problem.parkinglot.enums.VehicleType;
import lombok.Getter;
import lombok.NonNull;

import java.util.Objects;

public abstract class Vehicle {
    @Getter
    private final String licensePlate;
    @Getter
    private final VehicleType vehicleType;

    public Vehicle(@NonNull final String licensePlate, @NonNull final VehicleType vehicleType) {
        this.licensePlate = licensePlate;
        this.vehicleType = vehicleType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vehicle)) return false;
        Vehicle otherVehicle = (Vehicle) o;
        return licensePlate.equals(otherVehicle.getLicensePlate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(licensePlate);
    }
}
