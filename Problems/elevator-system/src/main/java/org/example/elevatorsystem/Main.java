package org.example.elevatorsystem;

import org.example.elevatorsystem.enums.Direction;
import org.example.elevatorsystem.interfaces.elevatorselection.MinDistanceElevatorSelectionStrategy;
import org.example.elevatorsystem.interfaces.elevatorselection.OddEvenElevatorSelectionStrategy;
import org.example.elevatorsystem.interfaces.requestselection.FCFSRequestSelectionStrategy;
import org.example.elevatorsystem.models.ElevatorCar;
import org.example.elevatorsystem.models.Floor;
import org.example.elevatorsystem.services.ElevatorSystem;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        ElevatorSystem elevatorSystem = ElevatorSystem.getInstance();

        elevatorSystem.setElevatorSelectionStrategy(new OddEvenElevatorSelectionStrategy());
//        elevatorSystem.setElevatorSelectionStrategy(new MinDistanceElevatorSelectionStrategy());

        elevatorSystem.setRequestSelectionStrategy(new FCFSRequestSelectionStrategy());

        for (int i = 0; i <= 40; i++) {
            Floor floor = new Floor(i);
            elevatorSystem.addFloor(floor);
        }

        for (int i = 0; i <= 5; i++) {
            ElevatorCar elevatorCar = new ElevatorCar(i);
            elevatorSystem.addElevatorCar(elevatorCar);
        }

//        int f1 = elevatorSystem.requestElevatorCar(0, Direction.UP);
//        int f2 = elevatorSystem.requestElevatorCar(5, Direction.DOWN);
//
//        elevatorSystem.selectFloorInElevatorCar(11, f1);
//        elevatorSystem.selectFloorInElevatorCar(1, f1);
//        elevatorSystem.selectFloorInElevatorCar(8, f2);

        // Simulate multiple concurrent elevator requests
        int numberOfThreads = 5; // Number of concurrent users
        Thread[] requestThreads = new Thread[numberOfThreads];
        Random random = new Random();

        for (int i = 0; i < numberOfThreads; i++) {
            requestThreads[i] = new Thread(() -> {
                int floorId = random.nextInt(41); // Random floor between 0 and 19
                Direction direction = random.nextBoolean() ? Direction.UP : Direction.DOWN; // Random direction

                if ((floorId == 0) && (direction == Direction.DOWN)) {
                    direction = Direction.UP;
                } else if ((floorId == 40) && (direction == Direction.UP)) {
                    direction = Direction.DOWN;
                }

                int elevatorCarId = elevatorSystem.requestElevatorCar(floorId, direction);
                System.out.println(Thread.currentThread().getName() + ": Requested Elevator Car: " + elevatorCarId + " at Floor " + floorId + ", Direction " + direction);

                // Simulate selecting a random floor within the elevator
                int destinationFloorId = random.nextInt(41);
                elevatorSystem.selectFloorInElevatorCar(destinationFloorId, elevatorCarId);
                System.out.println(Thread.currentThread().getName() + ": Selected Floor " + destinationFloorId + " in Elevator Car: " + elevatorCarId);
            });

            // Start the thread
            requestThreads[i].start();
        }

        // Wait for all threads to finish
        for (int i = 0; i < numberOfThreads; i++) {
            try {
                requestThreads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Elevator System simulation completed.");
    }
}
