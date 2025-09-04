package com.interview.problem.parkinglot.service;

import com.interview.problem.parkinglot.enums.VehicleType;
import com.interview.problem.parkinglot.model.ParkingTicket;
import com.interview.problem.parkinglot.strategy.parkingfee.ParkingFeeStrategy;
import lombok.Getter;
import lombok.NonNull;

public class ParkingFeeService {
    @Getter
    private ParkingFeeStrategy parkingFeeStrategy;

    public void setParkingFeeStrategy(@NonNull final ParkingFeeStrategy parkingFeeStrategy) {
        this.parkingFeeStrategy = parkingFeeStrategy;
    }

    public double calculateFee(@NonNull final ParkingTicket parkingTicket, @NonNull final VehicleType vehicleType) {
        return parkingFeeStrategy.calculateFee(vehicleType, parkingTicket.getEntryTime(), parkingTicket.getExitTime());
    }
}
