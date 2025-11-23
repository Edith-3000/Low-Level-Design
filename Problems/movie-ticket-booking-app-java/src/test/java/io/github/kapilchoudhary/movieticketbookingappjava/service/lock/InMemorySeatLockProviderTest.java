package io.github.kapilchoudhary.movieticketbookingappjava.service.lock;

import io.github.kapilchoudhary.movieticketbookingappjava.enums.SeatCategory;
import io.github.kapilchoudhary.movieticketbookingappjava.model.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InMemorySeatLockProviderTest {

    /**
     * These are PURE UNIT TESTS.
     *
     * Reason:
     * - We are testing only InMemorySeatLockProvider logic.
     * - No external services or real dependencies.
     * - Show, Seat, Movie, User, Screen are simple data objects (POJOs) → safe to instantiate.
     * - No mocking needed because the class has no service collaborators.
     */

    @Test
    void lockSeats_whenSeatNotAlreadyLocked_shouldSucceed() throws Exception {
        // Arrange
        InMemorySeatLockProvider provider = new InMemorySeatLockProvider(30);

        Movie movie = new Movie("MOVIE-ID-1", "Inception", 150);
        Screen screen = new Screen("SCREEN-ID-1", "Screen1");

        User user = new User("USER-ID-1", "Doland Trump", "doland.trump@epsteinisland.com");
        Show show = new Show("SHOW-ID-1", movie, screen, LocalDateTime.now(), 150);
        Seat seat = new Seat("SEAT-ID-1", "A", 1, SeatCategory.GOLD);

        // Act
        provider.lockSeats(user, show, List.of(seat));

        // Assert
        assertTrue(provider.getLockedSeats(show).contains(seat));
    }

    @Test
    void lockSeats_whenSeatAlreadyLocked_shouldThrowException() throws Exception {
        // Arrange
        InMemorySeatLockProvider provider = new InMemorySeatLockProvider(30);

        Movie movie = new Movie("MOVIE-ID-1", "Inception", 150);
        Screen screen = new Screen("SCREEN-ID-1", "Screen1");

        User user1 = new User("USER-ID-1", "Doland Trump", "doland.trump@epsteinisland.com");
        User user2 = new User("USER-ID-2", "Melania Modi", "melania.modi@mexico.com");
        Show show = new Show("SHOW-ID-1", movie, screen, LocalDateTime.now(), 150);
        Seat seat = new Seat("SEAT-ID-1", "A", 1, SeatCategory.GOLD);

        provider.lockSeats(user1, show, List.of(seat));

        // Act + Assert
        Exception ex = assertThrows(Exception.class, () ->
                provider.lockSeats(user2, show, List.of(seat))
        );

        assertTrue(ex.getMessage().contains("already locked"));
    }
}
