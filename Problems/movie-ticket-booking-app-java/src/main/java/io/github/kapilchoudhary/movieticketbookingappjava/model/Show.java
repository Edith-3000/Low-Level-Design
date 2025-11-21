package io.github.kapilchoudhary.movieticketbookingappjava.model;

import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDateTime;

@Getter
public class Show {

    private final String id;
    private final Movie movie;
    private final Screen screen;
    private final LocalDateTime startTime;
    private final int durationInMinutes;

    public Show(
            @NonNull final String id,
            @NonNull final Movie movie,
            @NonNull final Screen screen,
            @NonNull final LocalDateTime startTime,
            final int durationInMinutes
    ) {
        this.id = id;
        this.movie = movie;
        this.screen = screen;
        this.startTime = startTime;
        this.durationInMinutes = durationInMinutes;
    }
}
