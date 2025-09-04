package com.interview.problem.parkinglot.model;

import com.interview.problem.parkinglot.model.vehicle.Vehicle;
import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDateTime;

public class ParkingTicket {
    @Getter
    private final LocalDateTime entryTime;

    @Getter
    private LocalDateTime exitTime;

    @Getter
    private final Vehicle vehicle;

    public ParkingTicket(@NonNull final LocalDateTime entryTime, @NonNull final Vehicle vehicle) {
        this.entryTime = entryTime;
        this.vehicle = vehicle;
    }

    public void setExitTime(@NonNull final LocalDateTime exitTime) {
        this.exitTime = exitTime;
    }
}
