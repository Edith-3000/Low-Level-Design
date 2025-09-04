package com.interview.problem.parkinglot.model.parkingspot;

import com.interview.problem.parkinglot.enums.VehicleType;
import lombok.NonNull;

public class TruckParkingSpot extends ParkingSpot {
    public TruckParkingSpot(final int spotNumber) {
        super(spotNumber, VehicleType.TRUCK);
    }
}
