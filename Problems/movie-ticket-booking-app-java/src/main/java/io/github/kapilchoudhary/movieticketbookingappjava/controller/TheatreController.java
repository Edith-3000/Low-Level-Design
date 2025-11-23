package io.github.kapilchoudhary.movieticketbookingappjava.controller;

import io.github.kapilchoudhary.movieticketbookingappjava.enums.SeatCategory;
import io.github.kapilchoudhary.movieticketbookingappjava.service.TheatreService;
import lombok.NonNull;

public class TheatreController {

    private final TheatreService theatreService;

    public TheatreController(@NonNull final TheatreService theatreService) {
        this.theatreService = theatreService;
    }

    public String createTheatre(@NonNull final String theatreName) {
        return theatreService.createTheater(theatreName);
    }

    public String createScreenInTheatre(
            @NonNull final String screenName,
            @NonNull final String theatreId
    ) throws Exception {
        return theatreService.createScreenInTheatre(screenName, theatreId);
    }

    public String createSeatInScreen(
            @NonNull final String seatRowNumber,
            final int seatNumber,
            @NonNull final SeatCategory seatCategory,
            @NonNull final String screenId
    ) throws Exception {
        return theatreService.createSeatInScreen(seatRowNumber, seatNumber, seatCategory, screenId);
    }
}
