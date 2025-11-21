package io.github.kapilchoudhary.movieticketbookingappjava.service;

import io.github.kapilchoudhary.movieticketbookingappjava.enums.SeatCategory;
import io.github.kapilchoudhary.movieticketbookingappjava.model.Movie;
import io.github.kapilchoudhary.movieticketbookingappjava.model.Screen;
import io.github.kapilchoudhary.movieticketbookingappjava.model.Seat;
import io.github.kapilchoudhary.movieticketbookingappjava.model.Theatre;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class TheatreService {

    private final Map<String, Theatre> theatres;
    private final Map<String, Screen> screens;

    private final AtomicInteger theatreIdCounter;
    private final AtomicInteger screenIdCounter;
    private final AtomicInteger seatIdCounter;

    public TheatreService() {
        this.theatres = new HashMap<>();
        this.screens = new HashMap<>();

        theatreIdCounter = new AtomicInteger(0);
        screenIdCounter = new AtomicInteger(0);
        seatIdCounter = new AtomicInteger(0);
    }

    public String createTheater(@NonNull final String theatreName) {
        String theatreId = "THEATRE-ID-" + theatreIdCounter.incrementAndGet();

        Theatre theatre = new Theatre(theatreId, theatreName);
        theatres.put(theatreId, theatre);

        return theatreId;
    }

    public String createScreenInTheatre(
            @NonNull final String screenName,
            @NonNull final String theatreId
    ) throws Exception {
        Theatre theatre = getTheatreById(theatreId);

        String screenId = "SCREEN-ID-" + screenIdCounter.incrementAndGet();

        Screen screen = new Screen(screenId, screenName);
        screens.put(screenId, screen);
        theatre.addScreen(screen);

        return screenId;
    }

    public String createSeatInScreen(
            @NonNull final String seatRowNumber,
            final int seatNumber, @NonNull final
            SeatCategory seatCategory,
            @NonNull final String screenId
    ) throws Exception {
        Screen screen = getScreenById(screenId);

        String seatId = "SEAT-ID-" + seatIdCounter.incrementAndGet();

        Seat seat = new Seat(seatId, seatRowNumber, seatNumber, seatCategory);
        screen.addSeat(seat);

        return seatId;
    }

    public Theatre getTheatreById(@NonNull final String theatreId) throws Exception {
        if (!theatres.containsKey(theatreId)) {
            throw new Exception("Theatre with ID: " + theatreId + " not found.");
        }

        return theatres.get(theatreId);
    }

    public Screen getScreenById(@NonNull final String screenId) throws Exception {
        if (!screens.containsKey(screenId)) {
            throw new Exception("Screen with ID: " + screenId + " not found.");
        }

        return screens.get(screenId);
    }
}
