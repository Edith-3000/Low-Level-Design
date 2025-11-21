package io.github.kapilchoudhary.movieticketbookingappjava.model;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class Movie {

    private final String id;
    private final String name;
    private final int durationInMinutes;

    public Movie(
            @NonNull final String id,
            @NonNull final String name,
            @NonNull final int durationInMinutes
    ) {
        this.id = id;
        this.name = name;
        this.durationInMinutes = durationInMinutes;
    }
}
