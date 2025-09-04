package com.interview.problem.parkinglot.model.parkingspot;

import com.interview.problem.parkinglot.enums.VehicleType;

public class CarParkingSpot extends ParkingSpot {
    public CarParkingSpot(final int spotNumber) {
        super(spotNumber, VehicleType.CAR);
    }
}
