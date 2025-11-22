package io.github.kapilchoudhary.movieticketbookingappjava;

import io.github.kapilchoudhary.movieticketbookingappjava.controller.*;
import io.github.kapilchoudhary.movieticketbookingappjava.enums.SeatCategory;
import io.github.kapilchoudhary.movieticketbookingappjava.model.Booking;
import io.github.kapilchoudhary.movieticketbookingappjava.model.User;
import io.github.kapilchoudhary.movieticketbookingappjava.service.*;
import io.github.kapilchoudhary.movieticketbookingappjava.service.lock.InMemorySeatLockProvider;
import io.github.kapilchoudhary.movieticketbookingappjava.service.lock.SeatLockProvider;
import io.github.kapilchoudhary.movieticketbookingappjava.service.payment.UPIPaymentStrategy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MovieTicketBookingApplication {

    public static void main(String[] args) {
        try {
            System.out.println("Starting application: movie-ticket-booking-app-java...");

            // Initialize services
            MovieService movieService = new MovieService();
            TheatreService theatreService = new TheatreService();
            ShowService showService = new ShowService(theatreService, movieService);

            // Create a seat lock provider with a timeout in seconds
            SeatLockProvider seatLockProvider = new InMemorySeatLockProvider(120);

            // Initialize booking service with the seat lock provider
            BookingService bookingService = new BookingService(showService, seatLockProvider, theatreService);

            // Initialize seat availability service
            SeatAvailaibilityService seatAvailabilityService = new SeatAvailaibilityService(bookingService, seatLockProvider, showService);

            // Initialize payment service with a simple payment strategy and allowed retries for failures
            PaymentService paymentService = new PaymentService(2, new UPIPaymentStrategy(), bookingService);

            // Initialize controllers
            MovieController movieController = new MovieController(movieService);
            TheatreController theatreController = new TheatreController(theatreService);
            ShowController showController = new ShowController(showService);
            BookingController bookingController = new BookingController(bookingService);
            SeatAvailaibilityController seatAvailaibilityController = new SeatAvailaibilityController(seatAvailabilityService);
            PaymentController paymentController = new PaymentController(paymentService);

            // Step 1: Create a theatre
            System.out.println("\nCreating a new theatre...");
            String theatreId = theatreController.createTheatre("PVR Cinemas");
            System.out.println("Theatre created with ID: " + theatreId);

            // Step 2: Create a screen in the theatre
            System.out.println("\nCreating a new screen...");
            String screenId = theatreController.createScreenInTheatre("Screen 1", theatreId);
            System.out.println("Screen created with ID: " + screenId);

            // Step 3: Create seats in the screen
            System.out.println("\nCreating seats...");
            // Create 5 rows with 10 seats each
            List<String> seatIds = new ArrayList<>();
            for (char seatRowNumber = 'A'; seatRowNumber <= 'E'; seatRowNumber++) {
                SeatCategory category;
                if (seatRowNumber == 'A') {
                    category = SeatCategory.PLATINUM; // First row is premium
                } else if (seatRowNumber <= 'C') {
                    category = SeatCategory.GOLD;     // Next two rows are gold
                } else {
                    category = SeatCategory.SILVER;   // Rest are silver
                }

                for (int seatNumber = 1; seatNumber <= 10; seatNumber++) {
                    String seatId = theatreController.createSeatInScreen(String.valueOf(seatRowNumber), seatNumber, category, screenId);
                    System.out.println("Created seat at [row, col]: [" + seatRowNumber + seatNumber + "] with ID: " + seatId + " and category: " + category);
                    seatIds.add(seatId);
                }
            }

            // Step 4: Create a movie
            System.out.println("\nCreating a new movie...");
            String movieId = movieController.createMovie("Inception", 150);
            System.out.println("Movie created with ID: " + movieId);

            // Step 5: Create a show
            System.out.println("\nCreating a new show...");
            LocalDateTime showTime = LocalDateTime.now(); // Current time
            String showId = showController.createShow(movieId, screenId, showTime, 150);
            System.out.println("Show created with ID: " + showId);

            // Step 6: Get available seats for the show
            System.out.println("\nChecking available seats...");
            List<String> availableSeats = seatAvailaibilityController.getAvailableSeats(showId);
            System.out.println("Available seats: " + availableSeats);

            // Step 7: Create a user
            System.out.println("\nCreating a user...");
            User user = new User("USER-1", "Doland Trump", "doland.trump@cia.com");
            System.out.println("User created: " + user.getName() + " with email: " + user.getEmailAddress());

            // Step 8: Book tickets sequentially
            System.out.println("\nSequential booking of seats 1, 2, 3...");
            String bookingId = bookingController.createBooking(user, showId, List.of("SEAT-ID-1", "SEAT-ID-2", "SEAT-ID-3"));
            System.out.println("Booking created with ID: " + bookingId);

            // Step 9: Process payment for the booking
            System.out.println("\nProcessing payment...");
            paymentController.processPayment(bookingId, user);
            System.out.println("Payment processed successfully!");

            // Step 10: Verify booking status
            Booking booking = bookingService.getBookingById(bookingId);
            System.out.println("\nBooking status: " + booking.getBookingStatus());
            System.out.println("Is booking confirmed? " + booking.isBookingConfirmed());

            // Step 11: Check available seats again after booking
            System.out.println("\nChecking available seats after booking...");
            availableSeats = seatAvailaibilityController.getAvailableSeats(showId);
            System.out.println("Available seats: " + availableSeats);

            // ------------------------------
            // CONCURRENT BOOKING SIMULATION
            // ------------------------------
            System.out.println("\nSimulating concurrent booking attempts...");
        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
