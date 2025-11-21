package io.github.kapilchoudhary.movieticketbookingappjava.service;

import io.github.kapilchoudhary.movieticketbookingappjava.model.Booking;
import io.github.kapilchoudhary.movieticketbookingappjava.model.Seat;
import io.github.kapilchoudhary.movieticketbookingappjava.model.Show;
import io.github.kapilchoudhary.movieticketbookingappjava.model.User;
import lombok.NonNull;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class BookingService {

    private final Map<String, Booking> bookings;
    private final AtomicInteger bookingIdCounter;
    private final ShowService showService;

    public BookingService(@NonNull final ShowService showService) {
        this.bookings = new ConcurrentHashMap<>();
        this.bookingIdCounter = new AtomicInteger(0);
        this.showService = showService;
    }

    public String createBooking(
            @NonNull final User user,
            @NonNull final String showId,
            @NonNull final List<Seat> seats
    ) throws Exception {
        Show show = showService.getShowById(showId);

        
    }
}
