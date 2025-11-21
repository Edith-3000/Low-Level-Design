package io.github.kapilchoudhary.movieticketbookingappjava.model;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class User {

    private final String id;
    private final String name;
    private final String emailAddress;

    public User(
            @NonNull final String id,
            @NonNull final String name,
            @NonNull final String emailAddress
    ) {
        this.id = id;
        this.name = name;
        this.emailAddress = emailAddress;
    }
}
