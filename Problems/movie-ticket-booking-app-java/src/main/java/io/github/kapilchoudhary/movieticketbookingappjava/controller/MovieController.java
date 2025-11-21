package io.github.kapilchoudhary.movieticketbookingappjava.controller;

import io.github.kapilchoudhary.movieticketbookingappjava.service.MovieService;
import lombok.NonNull;

public class MovieController {

    private final MovieService movieService;

    public MovieController(@NonNull final MovieService movieService) {
        this.movieService = movieService;
    }

    public String createMovie(@NonNull final String name, final int durationInMinutes) {
        return movieService.createMovie(name, durationInMinutes);
    }
}
