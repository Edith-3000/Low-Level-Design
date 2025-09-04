package com.interview.problem.parkinglot.model;

import com.interview.problem.parkinglot.enums.VehicleType;
import com.interview.problem.parkinglot.model.parkingspot.ParkingSpot;
import com.interview.problem.parkinglot.model.vehicle.Vehicle;
import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParkingFloor {
    @Getter
    private final int parkingFloorId;

    private final List<ParkingSpot> parkingSpots;

    public ParkingFloor(final int parkingFloorId) {
        this.parkingFloorId = parkingFloorId;
        parkingSpots = new ArrayList<>();
    }

    public void addParkingSpot(@NonNull final ParkingSpot parkingSpot) {
        parkingSpots.add(parkingSpot);
    }

    public ParkingSpot findAvailableParkingSpot(@NonNull final VehicleType vehicleType) {
        for (ParkingSpot parkingSpot: parkingSpots) {
            try {
                parkingSpot.canParkVehicle(vehicleType);
                return parkingSpot;
            } catch (IllegalStateException | IllegalArgumentException ignored) {
            }
        }

        return null;
    }

    public boolean parkVehicle(@NonNull final Vehicle vehicle) {
        ParkingSpot parkingSpot = findAvailableParkingSpot(vehicle.getVehicleType());

        if (parkingSpot == null) {
            return false;
        }

        parkingSpot.parkVehicle(vehicle);

        System.out.println("Vehicle: " +
                vehicle.getLicensePlate() + " of type: " +
                vehicle.getVehicleType() + " parked at spot number: " +
                parkingSpot.getSpotNumber() + " of floor: " + this.parkingFloorId);

        return true;
    }

    public boolean removeVehicle(@NonNull final Vehicle vehicle) {
        for (ParkingSpot parkingSpot: parkingSpots) {
            if (vehicle.equals(parkingSpot.getVehicle())) {
                parkingSpot.removeVehicle();

                System.out.println("Vehicle: " +
                        vehicle.getLicensePlate() + " of type: " +
                        vehicle.getVehicleType() + " removed from spot number: " +
                        parkingSpot.getSpotNumber() + " of floor: " + this.parkingFloorId);

                return true;
            }
        }

        return false;
    }

    public List<ParkingSpot> getParkingSpots() {
        return Collections.unmodifiableList(parkingSpots);
    }
}
