package io.github.kapilchoudhary.movieticketbookingappjava.service;

import io.github.kapilchoudhary.movieticketbookingappjava.model.Movie;
import io.github.kapilchoudhary.movieticketbookingappjava.model.Screen;
import io.github.kapilchoudhary.movieticketbookingappjava.model.Seat;
import io.github.kapilchoudhary.movieticketbookingappjava.model.Show;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ShowService {

    private final Map<String, Show> shows;
    private final AtomicInteger showIdCounter;
    private final TheatreService theatreService;
    private final MovieService movieService;

    public ShowService(
            @NonNull final TheatreService theatreService,
            @NonNull final MovieService movieService
    ) {
        this.shows = new HashMap<>();
        this.showIdCounter = new AtomicInteger(0);
        this.theatreService = theatreService;
        this.movieService = movieService;
    }

    public String createShow(
            @NonNull final String movieId,
            @NonNull final String screenId,
            @NonNull final LocalDateTime startTime,
            final int durationInMinutes
    ) throws Exception {
        Movie movie = movieService.getMovieById(movieId);
        Screen screen = theatreService.getScreenById(screenId);

        String showId = "SHOW-ID-" + showIdCounter.incrementAndGet();

        Show show = new Show(showId, movie, screen, startTime, durationInMinutes);
        shows.put(showId, show);

        return showId;
    }

    public Show getShowById(@NonNull final String showId) throws Exception {
        if (!shows.containsKey(showId)) {
            throw new Exception("Show with ID: " + showId + " not found.");
        }

        return shows.get(showId);
    }
}
