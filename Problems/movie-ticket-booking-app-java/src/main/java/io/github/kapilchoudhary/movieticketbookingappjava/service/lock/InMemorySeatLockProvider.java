package io.github.kapilchoudhary.movieticketbookingappjava.service.lock;

import io.github.kapilchoudhary.movieticketbookingappjava.model.Seat;
import io.github.kapilchoudhary.movieticketbookingappjava.model.SeatLock;
import io.github.kapilchoudhary.movieticketbookingappjava.model.Show;
import io.github.kapilchoudhary.movieticketbookingappjava.model.User;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InMemorySeatLockProvider implements SeatLockProvider {

    private final Map<Show, Map<Seat, SeatLock>> locks;

    private final int lockTimeout;

    public InMemorySeatLockProvider(final int lockTimeout) {
        this.locks = new ConcurrentHashMap<>();
        this.lockTimeout = lockTimeout;
    }

    @Override
    public void lockSeats(
            @NonNull final User user,
            @NonNull final Show show,
            @NonNull final List<Seat> seats
    ) throws Exception {
        Map<Seat, SeatLock> showLocks = locks.computeIfAbsent(show, s -> new ConcurrentHashMap<>());

        synchronized (showLocks) {
            for (Seat seatToBeLocked: seats) {
                if (showLocks.containsKey(seatToBeLocked)) {
                    SeatLock seatLock = showLocks.get(seatToBeLocked);

                    if (!seatLock.isLockExpired()) {
                        throw new Exception("Seat ID: " + seatToBeLocked.getId() + " already locked for Show ID: " + show.getId());
                    }
                }
            }

            LocalDateTime seatLockTime = LocalDateTime.now();

            for (Seat seatToBeLocked: seats) {
                SeatLock seatLock = new SeatLock(seatToBeLocked, show, seatLockTime, lockTimeout, user);
                showLocks.put(seatToBeLocked, seatLock);
            }
        }
    }

    @Override
    public void validateLocks(
            @NonNull final User user,
            @NonNull final Show show,
            @NonNull final List<Seat> seats
    ) throws Exception {
        Map<Seat, SeatLock> showLocks = locks.get(show);
        if (showLocks == null) {
            throw new Exception("Lock validation failed, no locks present for Show ID " + show.getId());
        }

        synchronized (showLocks) {
            for (Seat seatAssumedToBeLocked: seats) {
                if (showLocks.containsKey(seatAssumedToBeLocked)) {
                    SeatLock seatLock = showLocks.get(seatAssumedToBeLocked);

                    if (seatLock.isLockExpired()) {
                        throw new Exception("Lock expired for Seat ID: " + seatAssumedToBeLocked.getId() + " for Show ID: " + show.getId());
                    } else if (!seatLock.getLockedBy().equals(user)) {
                        throw new Exception(
                                "Lock validation for User ID " + user.getId() + " failed, seat lock for Seat ID: "
                                        + seatAssumedToBeLocked.getId() + " for Show ID: " + show.getId()
                                        + " is currently held by User ID " + seatLock.getLockedBy());
                    }
                } else {
                    throw new Exception("Lock validation failed, seat lock not present for Seat ID: " + seatAssumedToBeLocked.getId() + " for Show ID: " + show.getId());
                }
            }
        }
    }

    @Override
    public void unlockSeats(
            @NonNull final User user,
            @NonNull final Show show,
            @NonNull final List<Seat> seats
    ) throws Exception {
        Map<Seat, SeatLock> showLocks = locks.get(show);
        if (showLocks == null) {
            throw new Exception("Lock validation failed, no locks present for Show ID " + show.getId());
        }

        synchronized (showLocks) {
            for (Seat seat: seats) {
                if (showLocks.containsKey(seat)) {
                    SeatLock seatLock = showLocks.get(seat);

                    if (seatLock.getLockedBy().equals(user)) {
                        showLocks.remove(seat);
                    }
                }
            }
        }
    }

    @Override
    public List<Seat> getLockedSeats(@NonNull final Show show) {
        Map<Seat, SeatLock> showLocks = locks.get(show);
        if (showLocks == null) {
            return Collections.emptyList();
        }

        synchronized (showLocks) {
            return showLocks.entrySet().stream()
                    .filter(entry -> !entry.getValue().isLockExpired())
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
        }
    }
}
