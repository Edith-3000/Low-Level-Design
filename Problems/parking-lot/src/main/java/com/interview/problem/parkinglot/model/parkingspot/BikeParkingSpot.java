package com.interview.problem.parkinglot.model.parkingspot;

import com.interview.problem.parkinglot.enums.VehicleType;

public class BikeParkingSpot extends ParkingSpot {
    public BikeParkingSpot(final int spotNumber) {
        super(spotNumber, VehicleType.BIKE);
    }
}
