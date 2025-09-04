package com.interview.problem.parkinglot.strategy.parkingfee;

import com.interview.problem.parkinglot.enums.VehicleType;
import lombok.NonNull;

import java.time.LocalDateTime;

// Interface for Parking Fee Calculation Strategy
public interface ParkingFeeStrategy {
    /**
     - Calculate parking fee based on vehicle type and duration
     -
     - @param vehicleType Type of vehicle being parked
     - @param entryTime Entry time of the vehicle
     - @param exitTime Exit time of the vehicle
     - @return Calculated parking fee
     */
    double calculateFee(@NonNull final VehicleType vehicleType, @NonNull final LocalDateTime entryTime, @NonNull final LocalDateTime exitTime);
}
