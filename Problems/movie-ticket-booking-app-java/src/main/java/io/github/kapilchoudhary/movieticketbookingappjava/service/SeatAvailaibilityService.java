package io.github.kapilchoudhary.movieticketbookingappjava.service;

import io.github.kapilchoudhary.movieticketbookingappjava.model.Seat;
import io.github.kapilchoudhary.movieticketbookingappjava.model.Show;
import io.github.kapilchoudhary.movieticketbookingappjava.service.lock.SeatLockProvider;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

public class SeatAvailaibilityService {

    private final BookingService bookingService;
    private final SeatLockProvider seatLockProvider;
    private final ShowService showService;

    public SeatAvailaibilityService(
            @NonNull final BookingService bookingService,
            @NonNull final SeatLockProvider seatLockProvider,
            @NonNull final ShowService showService
    ) {
        this.bookingService = bookingService;
        this.seatLockProvider = seatLockProvider;
        this.showService = showService;
    }

    public List<Seat> getAvailableSeats(@NonNull final String showId) throws Exception {
        Show show = showService.getShowById(showId);

        List<Seat> allSeats = show.getScreen().getSeats();
        List<Seat> unavailableSeats = getUnavailableSeats(show);

        List<Seat> availableSeats = new ArrayList<>(allSeats);
        availableSeats.removeAll(unavailableSeats);

        return availableSeats;
    }

    public List<Seat> getUnavailableSeats(@NonNull final Show show) {
        List<Seat> bookedSeats = bookingService.getAllBookedAndConfirmedSeatsForShow(show);
        List<Seat> lockedSeats = seatLockProvider.getLockedSeats(show);

        List<Seat> unavailableSeats = bookedSeats;
        unavailableSeats.addAll(lockedSeats);

        return unavailableSeats;
    }
}
