package io.github.kapilchoudhary.movieticketbookingappjava.model;

import io.github.kapilchoudhary.movieticketbookingappjava.enums.SeatCategory;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class Seat {

    private final String id;
    private final String rowNumber;
    private final int seatNumber; // column
    private final SeatCategory seatCategory;

    public Seat(
            @NonNull final String id,
            @NonNull final String rowNumber,
            final int seatNumber,
            @NonNull final SeatCategory seatCategory
    ) {
        this.id = id;
        this.rowNumber = rowNumber;
        this.seatNumber = seatNumber;
        this.seatCategory = seatCategory;
    }
}
