package com.interview.problem.parkinglot.service;

import com.interview.problem.parkinglot.model.ParkingFloor;
import com.interview.problem.parkinglot.model.ParkingTicket;
import com.interview.problem.parkinglot.model.vehicle.Vehicle;
import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingLotService {
    @Getter
    private final int id;

    @Getter
    private final ParkingFeeService parkingFeeService;

    @Getter
    private final PaymentService paymentService;

    @Getter
    private final String name;

    @Getter
    private final String location;

    private final List<ParkingFloor> parkingFloors;
    private final Map<String, ParkingTicket> parkingTicketMap;

    private ParkingLotService(Builder builder) {
        this.id = builder.id;
        this.parkingFeeService = builder.parkingFeeService;
        this.paymentService = builder.paymentService;
        this.name = builder.name;
        this.location = builder.location;
        this.parkingFloors = builder.parkingFloors;
        this.parkingTicketMap = builder.parkingTicketMap;
    }

    public List<ParkingFloor> getParkingFloors() {
        return Collections.unmodifiableList(parkingFloors);
    }

    // Just an overkill to use builder here, but did it for the sake of learning
    public static class Builder {
        private final int id;
        private final ParkingFeeService parkingFeeService;
        private final PaymentService paymentService;
        private String name = "";
        private String location = "";
        private List<ParkingFloor> parkingFloors = null;
        private Map<String, ParkingTicket> parkingTicketMap = new HashMap<>();

        public Builder(final int id, @NonNull final ParkingFeeService parkingFeeService, @NonNull final PaymentService paymentService) {
            this.id = id;
            this.paymentService = paymentService;
            this.parkingFeeService = parkingFeeService;
        }

        public Builder withName(@NonNull final String name) {
            this.name = name;
            return this;
        }

        public Builder withLocation(@NonNull final String location) {
            this.location = location;
            return this;
        }

        public Builder withParkingFloors(@NonNull final List<ParkingFloor> parkingFloors) {
            this.parkingFloors = parkingFloors;
            return this;
        }

        public ParkingLotService build() {
            return new ParkingLotService(this);
        }
    }

    public void vehicleEntry(@NonNull final Vehicle vehicle) {
        for (ParkingFloor parkingFloor: parkingFloors) {
            if (parkingFloor.parkVehicle(vehicle)) {
                ParkingTicket parkingTicket = new ParkingTicket(LocalDateTime.now(), vehicle);
                parkingTicketMap.put(vehicle.getLicensePlate(), parkingTicket);
                return;
            }
        }

        System.out.println("Could not find any " + vehicle.getVehicleType() + " parking spot for vehicle: " + vehicle.getLicensePlate());
    }

    public void vehicleExit(@NonNull final Vehicle vehicle) {
        boolean parkingSpotFreed = false;

        // Free the parking spot by finding the spot holding this vehicle
        for (ParkingFloor parkingFloor : parkingFloors) {
            if (parkingFloor.removeVehicle(vehicle)) {
                parkingSpotFreed = true;
                break;
            }
        }

        if (!parkingSpotFreed) {
            throw new IllegalStateException("Vehicle: " + vehicle.getLicensePlate() + " has no entry in system");
        }

        ParkingTicket parkingTicket = parkingTicketMap.get(vehicle.getLicensePlate());

        if (parkingTicket == null) {
            throw new IllegalStateException("Vehicle: " + vehicle.getLicensePlate() + " has no entry in system");
        }

        parkingTicket.setExitTime(LocalDateTime.now());

        double parkingFee = parkingFeeService.calculateFee(parkingTicket, vehicle.getVehicleType());
        System.out.println("Parking fee calculated for vehicle: " + vehicle.getVehicleType() + " is Rs. " + parkingFee);

        paymentService.makePayment(parkingFee);
    }
}
