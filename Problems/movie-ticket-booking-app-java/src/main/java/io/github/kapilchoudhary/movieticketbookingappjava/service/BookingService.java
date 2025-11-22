package io.github.kapilchoudhary.movieticketbookingappjava.service;

import io.github.kapilchoudhary.movieticketbookingappjava.model.*;
import io.github.kapilchoudhary.movieticketbookingappjava.service.lock.SeatLockProvider;
import lombok.NonNull;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class BookingService {

    private final Map<String, Booking> bookings;
    private final AtomicInteger bookingIdCounter;
    private final ShowService showService;
    private final SeatLockProvider seatLockProvider;
    private final TheatreService theatreService;

    public BookingService(
            @NonNull final ShowService showService,
            @NonNull final SeatLockProvider seatLockProvider,
            @NonNull final TheatreService theatreService
    ) {
        this.bookings = new ConcurrentHashMap<>();
        this.bookingIdCounter = new AtomicInteger(0);
        this.showService = showService;
        this.seatLockProvider = seatLockProvider;
        this.theatreService = theatreService;
    }

    public String createBooking(
            @NonNull final User user,
            @NonNull final String showId,
            @NonNull final List<String> seatIds
    ) throws Exception {
        Show show = showService.getShowById(showId);
        List<Seat> seats = theatreService.getSeatsByIds(seatIds);

        if (isAnySeatAlreadyBookedAndConfirmed(show, seats)) {
            throw new Exception("Could not proceed, one or more seats already booked by other user(s). Please select other seats.");
        }

        seatLockProvider.lockSeats(user, show, seats);

        String bookingId = "BOOKING-ID-" + bookingIdCounter.incrementAndGet();

        Booking booking = new Booking(bookingId, user, show, seats);
        bookings.put(bookingId, booking);

        return bookingId;
    }

    private boolean isAnySeatAlreadyBookedAndConfirmed(
            @NonNull final Show show,
            @NonNull final List<Seat> toBeBookedSeats
    ) {
        List<Seat> alreadyBookedAndConfirmedSeats = getAllBookedAndConfirmedSeatsForShow(show);

        for (Seat toBeBookedSeat: toBeBookedSeats) {
            if (alreadyBookedAndConfirmedSeats.contains(toBeBookedSeat)) {
                return true;
            }
        }

        return false;
    }

    public List<Seat> getAllBookedAndConfirmedSeatsForShow(@NonNull final Show show) {
        return getAllBookingsForShow(show).stream()
                .filter(Booking::isBookingConfirmed)
                .map(Booking::getSeatsBooked)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private List<Booking> getAllBookingsForShow(@NonNull final Show show) {
        List<Booking> showBookings = new ArrayList<>();

        for (Booking booking: bookings.values()) {
            if (booking.getShow().equals(show)) {
                showBookings.add(booking);
            }
        }

        return showBookings;
    }

    public Booking getBookingById(@NonNull final String bookingId) throws Exception {
        if (!bookings.containsKey(bookingId)) {
            throw new Exception("Booking with ID: " + bookingId + " not found.");
        }

        return bookings.get(bookingId);
    }

    public void confirmBooking(@NonNull final Booking booking, @NonNull User user) throws Exception {
        if (!booking.getUser().getId().equals(user.getId())) {
            throw new Exception("Booking ID: " + booking.getId() + " has User ID: " + booking.getUser().getId() + ". Cannot confirm it for User ID " + user.getId());
        }

        seatLockProvider.validateLocks(user, booking.getShow(), booking.getSeatsBooked());

        booking.confirmBooking();
    }

    public void expireBooking(@NonNull final Booking booking, @NonNull User user) throws Exception {
        if (!booking.getUser().getId().equals(user.getId())) {
            throw new Exception("Booking ID: " + booking.getId() + " has User ID: " + booking.getUser().getId() + ". Cannot expire it for User ID " + user.getId());
        }

        seatLockProvider.unlockSeats(user, booking.getShow(), booking.getSeatsBooked());

        booking.expireBooking();
    }
}
