package io.github.kapilchoudhary.movieticketbookingappjava.model;

import io.github.kapilchoudhary.movieticketbookingappjava.enums.SeatCategory;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SeatLockTest {

    /**
     * These are PURE UNIT TESTS.
     *
     * Reason:
     * - We are testing only SeatLock behavior.
     * - No external services, DB, or frameworks involved.
     * - Only simple model objects are used as test data.
     * - No mocking required because SeatLock has no dependencies.
     */

    @Test
    void isLockExpired_whenTimeoutPassed_shouldReturnTrue() {
        // Arrange
        Movie movie = new Movie("M1", "Inception", 150);
        Screen screen = new Screen("S1", "Screen1");

        Seat seat = new Seat("SEAT-ID-1", "A", 1, SeatCategory.GOLD);
        Show show = new Show("SHOW-ID-1", movie, screen, LocalDateTime.now(), 150);
        User user = new User("USER-ID-1", "Doland Trump", "doland.trump@epsteinisland.com");

        LocalDateTime lockTime = LocalDateTime.now().minusSeconds(10);
        SeatLock lock = new SeatLock(seat, show, lockTime, 5, user);

        // Act
        boolean expired = lock.isLockExpired();

        // Assert
        assertTrue(expired);
    }

    @Test
    void isLockExpired_whenTimeoutNotPassed_shouldReturnFalse() {
        // Arrange
        Movie movie = new Movie("M1", "Inception", 150);
        Screen screen = new Screen("S1", "Screen1");

        Seat seat = new Seat("SEAT-ID-1", "A", 1, SeatCategory.GOLD);
        Show show = new Show("SHOW-ID-1", movie, screen, LocalDateTime.now(), 150);
        User user = new User("USER-ID-1", "Doland Trump", "doland.trump@epsteinisland.com");

        LocalDateTime lockTime = LocalDateTime.now();
        SeatLock lock = new SeatLock(seat, show, lockTime, 60, user);

        // Act
        boolean expired = lock.isLockExpired();

        // Assert
        assertFalse(expired);
    }
}
