package io.github.kapilchoudhary.movieticketbookingappjava.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class Screen {

    private final String id;
    private final String name;

    @Getter(AccessLevel.NONE)
    private final List<Seat> seats;

    public Screen(
            @NonNull final String id,
            @NonNull final String name
    ) {
        this.id = id;
        this.name = name;
        this.seats = new ArrayList<>();
    }

    public void addSeat(@NonNull final Seat seat) {
        seats.add(seat);
    }

    public List<Seat> getSeats() {
        return Collections.unmodifiableList(seats);
    }
}
