package com.interview.problem.parkinglot.strategy.parkingfee;

import com.interview.problem.parkinglot.enums.VehicleType;
import lombok.NonNull;

import java.time.Duration;
import java.time.LocalDateTime;

public class PremiumFeeStrategy implements ParkingFeeStrategy {
    @Override
    public double calculateFee(@NonNull final VehicleType vehicleType, @NonNull final LocalDateTime entryTime, @NonNull final LocalDateTime exitTime) {
        System.out.println("Calculating parking fee for vehicle type: " + vehicleType + " from " + "[" + entryTime + ", " + exitTime + "]");

        long hours = Duration.between(entryTime, exitTime).toHours();

        if (hours == 0) {
            hours = 1;
        }

        switch (vehicleType) {
            case BIKE:
                return 20 + (hours * 20);

            case CAR:
                return 40 + (hours * 40);

            case TRUCK:
                return 60 + (hours * 60);

            default:
                throw new IllegalArgumentException("Unknown vehicle type: " + vehicleType);
        }
    }
}
