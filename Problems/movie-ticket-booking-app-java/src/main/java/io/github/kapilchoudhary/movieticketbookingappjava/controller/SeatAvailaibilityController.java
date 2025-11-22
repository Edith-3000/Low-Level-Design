package io.github.kapilchoudhary.movieticketbookingappjava.controller;

import io.github.kapilchoudhary.movieticketbookingappjava.model.Seat;
import io.github.kapilchoudhary.movieticketbookingappjava.service.SeatAvailaibilityService;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

public class SeatAvailaibilityController {

    private final SeatAvailaibilityService seatAvailaibilityService;

    public SeatAvailaibilityController(@NonNull final SeatAvailaibilityService seatAvailaibilityService) {
        this.seatAvailaibilityService = seatAvailaibilityService;
    }

    public List<String> getAvailableSeats(@NonNull final String showId) throws Exception {
        List<Seat> availableSeats = seatAvailaibilityService.getAvailableSeats(showId);

        List<String> availableSeatsIds = new ArrayList<>();

        for (Seat seat: availableSeats) {
            String response = "(" + seat.getRowNumber() + seat.getSeatNumber() + ": " + seat.getId() + ")";
            availableSeatsIds.add(response);
        }

        return availableSeatsIds;
    }
}
