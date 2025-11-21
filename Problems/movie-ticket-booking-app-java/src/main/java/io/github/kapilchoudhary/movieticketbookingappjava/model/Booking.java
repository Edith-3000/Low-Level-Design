package io.github.kapilchoudhary.movieticketbookingappjava.model;

import io.github.kapilchoudhary.movieticketbookingappjava.enums.BookingStatus;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

@Getter
public class Booking {

    private final String id;
    private final User user;
    private final Show show;
    private final List<Seat> seatsBooked;

    @Setter
    private BookingStatus bookingStatus;

    public Booking(
            @NonNull final String id,
            @NonNull final User user,
            @NonNull final Show show,
            @NonNull final List<Seat> seatsBooked
    ) {
        this.id = id;
        this.user = user;
        this.show = show;
        this.seatsBooked = seatsBooked;
        this.bookingStatus = BookingStatus.CREATED;
    }
}
