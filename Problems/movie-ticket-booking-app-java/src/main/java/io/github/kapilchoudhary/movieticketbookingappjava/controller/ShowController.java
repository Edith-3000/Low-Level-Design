package io.github.kapilchoudhary.movieticketbookingappjava.controller;

import io.github.kapilchoudhary.movieticketbookingappjava.service.ShowService;
import lombok.NonNull;

import java.time.LocalDateTime;

public class ShowController {

    private final ShowService showService;

    public ShowController(@NonNull final ShowService showService) {
        this.showService = showService;
    }

    public String createShow(
            @NonNull final String movieId,
            @NonNull final String screenId,
            @NonNull final LocalDateTime startTime,
            final int durationInMinutes
    ) throws Exception {
        return showService.createShow(movieId, screenId, startTime, durationInMinutes);
    }
}
