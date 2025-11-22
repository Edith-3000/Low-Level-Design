package io.github.kapilchoudhary.movieticketbookingappjava.model;

import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDateTime;

@Getter
public class SeatLock {

    private final Seat seat;
    private final Show show;
    private final LocalDateTime lockTime;
    private final int lockExpiryTimeoutInSeconds;
    private final User lockedBy;

    public SeatLock(
            @NonNull final Seat seat,
            @NonNull final Show show,
            @NonNull final LocalDateTime lockTime,
            final int lockExpiryTimeoutInSeconds,
            @NonNull final User lockedBy
    ) {
        this.seat = seat;
        this.show = show;
        this.lockTime = lockTime;
        this.lockExpiryTimeoutInSeconds = lockExpiryTimeoutInSeconds;
        this.lockedBy = lockedBy;
    }

    public boolean isLockExpired() {
        LocalDateTime expiryTime = lockTime.plusSeconds(lockExpiryTimeoutInSeconds);
        return expiryTime.isBefore(LocalDateTime.now());
    }
}
