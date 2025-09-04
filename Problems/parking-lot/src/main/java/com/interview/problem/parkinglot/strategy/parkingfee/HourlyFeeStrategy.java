package com.interview.problem.parkinglot.strategy.parkingfee;

import com.interview.problem.parkinglot.enums.VehicleType;
import lombok.Getter;
import lombok.NonNull;

import java.time.Duration;
import java.time.LocalDateTime;

public class HourlyFeeStrategy implements ParkingFeeStrategy {
    @Getter
    private final double hourlyRate;

    public HourlyFeeStrategy(final double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    @Override
    public double calculateFee(@NonNull final VehicleType vehicleType, @NonNull final LocalDateTime entryTime, @NonNull final LocalDateTime exitTime) {
        System.out.println("Calculating parking fee for vehicle type: " + vehicleType + " from " + "[" + entryTime + ", " + exitTime + "]");

        long hours = Duration.between(entryTime, exitTime).toHours();

        if (hours == 0) {
            hours = 1;
        }

        return hours * hourlyRate;
    }
}
