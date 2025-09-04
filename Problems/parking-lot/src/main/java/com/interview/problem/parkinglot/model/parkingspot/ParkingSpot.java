package com.interview.problem.parkinglot.model.parkingspot;

import com.interview.problem.parkinglot.enums.VehicleType;
import com.interview.problem.parkinglot.model.vehicle.Vehicle;
import lombok.Getter;
import lombok.NonNull;

public class ParkingSpot {
    @Getter
    private int spotNumber;
    @Getter
    private final VehicleType spotType;
    @Getter
    private boolean isOccupied;
    @Getter
    private Vehicle vehicle;

    public ParkingSpot(final int spotNumber, @NonNull final VehicleType spotType) {
        this.spotNumber = spotNumber;
        this.spotType = spotType;
        this.isOccupied = false;
        this.vehicle = null;
    }

    public void canParkVehicle(@NonNull final VehicleType vehicleType) {
        if (this.isOccupied) {
            throw new IllegalStateException("Parking spot already occupied");
        }

        if (!this.spotType.equals(vehicleType)) {
            throw new IllegalArgumentException("Spot of type " + spotType + " not suitable for " + vehicleType);
        }
    }

    public void parkVehicle(@NonNull final Vehicle vehicle) {
        canParkVehicle(vehicle.getVehicleType());

        this.isOccupied = true;
        this.vehicle = vehicle;
    }

    public void removeVehicle() {
        if (!this.isOccupied) {
            throw new IllegalStateException("Parking spot already vacant");
        }

        this.isOccupied = false;
        this.vehicle = null;
    }
}
