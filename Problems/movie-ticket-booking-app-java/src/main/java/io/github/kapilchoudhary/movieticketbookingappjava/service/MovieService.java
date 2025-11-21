package io.github.kapilchoudhary.movieticketbookingappjava.service;

import io.github.kapilchoudhary.movieticketbookingappjava.model.Movie;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class MovieService {

    private final Map<String, Movie> movies;
    private final AtomicInteger movieIdCounter;

    public MovieService() {
        movies = new HashMap<>();
        movieIdCounter = new AtomicInteger(0);
    }

    public String createMovie(@NonNull final String name, final int durationInMinutes) {
        String movieId = "MOVIE-ID-" + movieIdCounter.incrementAndGet();

        Movie movie = new Movie(movieId, name, durationInMinutes);
        movies.put(movieId, movie);

        return movieId;
    }

    public Movie getMovieById(@NonNull final String movieId) throws Exception {
        if (!movies.containsKey(movieId)) {
            throw new Exception("Movie with ID: " + movieId + " not found.");
        }

        return movies.get(movieId);
    }
}
