package com.interview.problem.parkinglot;

import com.interview.problem.parkinglot.factory.vehicle.BikeFactory;
import com.interview.problem.parkinglot.factory.vehicle.CarFactory;
import com.interview.problem.parkinglot.factory.vehicle.TruckFactory;
import com.interview.problem.parkinglot.factory.vehicle.VehicleFactory;
import com.interview.problem.parkinglot.model.ParkingFloor;
import com.interview.problem.parkinglot.model.parkingspot.BikeParkingSpot;
import com.interview.problem.parkinglot.model.parkingspot.CarParkingSpot;
import com.interview.problem.parkinglot.model.parkingspot.ParkingSpot;
import com.interview.problem.parkinglot.model.parkingspot.TruckParkingSpot;
import com.interview.problem.parkinglot.model.vehicle.Vehicle;
import com.interview.problem.parkinglot.service.ParkingFeeService;
import com.interview.problem.parkinglot.service.ParkingLotService;
import com.interview.problem.parkinglot.service.PaymentService;
import com.interview.problem.parkinglot.strategy.parkingfee.HourlyFeeStrategy;
import com.interview.problem.parkinglot.strategy.parkingfee.ParkingFeeStrategy;
import com.interview.problem.parkinglot.strategy.payment.CashPaymentStrategy;
import com.interview.problem.parkinglot.strategy.payment.PaymentStrategy;

import java.util.ArrayList;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        ParkingFeeStrategy parkingFeeStrategy = new HourlyFeeStrategy(10);

        ParkingFeeService parkingFeeService = new ParkingFeeService();
        parkingFeeService.setParkingFeeStrategy(parkingFeeStrategy);

        PaymentStrategy paymentStrategy = new CashPaymentStrategy();

        PaymentService paymentService = new PaymentService();
        paymentService.setPaymentStrategy(paymentStrategy);

        ParkingSpot parkingSpot11 = new BikeParkingSpot(1);
        ParkingSpot parkingSpot12 = new CarParkingSpot(2);

        ParkingSpot parkingSpot21 = new CarParkingSpot(1);
        ParkingSpot parkingSpot22 = new TruckParkingSpot(2);

        ParkingFloor parkingFloor1 = new ParkingFloor(1);
        parkingFloor1.addParkingSpot(parkingSpot11);
        parkingFloor1.addParkingSpot(parkingSpot12);

        ParkingFloor parkingFloor2 = new ParkingFloor(2);
        parkingFloor2.addParkingSpot(parkingSpot21);
        parkingFloor2.addParkingSpot(parkingSpot22);

        List<ParkingFloor> parkingFloors = new ArrayList<>();
        parkingFloors.add(parkingFloor1);
        parkingFloors.add(parkingFloor2);

        ParkingLotService parkingLotService = new ParkingLotService.Builder(1, parkingFeeService, paymentService)
                .withName("PARKING_LOT_3000")
                .withLocation("Atrium Mall, Meera Road")
                .withParkingFloors(parkingFloors).build();

        VehicleFactory bikeFactory = new BikeFactory();
        VehicleFactory carFactory = new CarFactory();
        VehicleFactory truckFactory = new TruckFactory();

        Vehicle bike1 = bikeFactory.createVehicle("HR-01-007");
//        Vehicle bike2 = bikeFactory.createVehicle("TS-08-008");
        Vehicle car1 = carFactory.createVehicle("TG-07-009");
        Vehicle car2 = carFactory.createVehicle("MH-00-789");
        Vehicle truck1 = truckFactory.createVehicle("AP-16-978");

        parkingLotService.vehicleEntry(bike1);
//        parkingLotService.vehicleEntry(bike2);
        parkingLotService.vehicleEntry(car1);
//        parkingLotService.vehicleExit(bike2);
        parkingLotService.vehicleExit(bike1);
        parkingLotService.vehicleExit(car1);
    }
}
