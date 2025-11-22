package io.github.kapilchoudhary.movieticketbookingappjava.controller;

import io.github.kapilchoudhary.movieticketbookingappjava.model.Seat;
import io.github.kapilchoudhary.movieticketbookingappjava.model.User;
import io.github.kapilchoudhary.movieticketbookingappjava.service.BookingService;
import lombok.NonNull;

import java.util.List;

public class BookingController {

    private final BookingService bookingService;

    public BookingController(@NonNull final BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public String createBooking(
            @NonNull final User user,
            @NonNull final String showId,
            @NonNull final List<String> seatIds
    ) throws Exception {
        return bookingService.createBooking(user, showId, seatIds);
    }
}
