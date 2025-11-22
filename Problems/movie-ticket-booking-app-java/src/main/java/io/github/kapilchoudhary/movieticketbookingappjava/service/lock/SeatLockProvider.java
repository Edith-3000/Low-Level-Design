package io.github.kapilchoudhary.movieticketbookingappjava.service.lock;

import io.github.kapilchoudhary.movieticketbookingappjava.model.Seat;
import io.github.kapilchoudhary.movieticketbookingappjava.model.Show;
import io.github.kapilchoudhary.movieticketbookingappjava.model.User;
import lombok.NonNull;

import java.util.List;

public interface SeatLockProvider {
    void lockSeats(@NonNull final User user, @NonNull final Show show, @NonNull final List<Seat> seats) throws Exception;
    void validateLocks(@NonNull final User user, @NonNull final Show show, @NonNull final List<Seat> seats) throws Exception;
    void unlockSeats(@NonNull final User user, @NonNull final Show show, @NonNull final List<Seat> seats) throws Exception;
    List<Seat> getLockedSeats(@NonNull final Show show);
}
