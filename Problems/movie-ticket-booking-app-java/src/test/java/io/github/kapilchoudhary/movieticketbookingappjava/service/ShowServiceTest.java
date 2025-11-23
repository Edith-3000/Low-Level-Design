package io.github.kapilchoudhary.movieticketbookingappjava.service;

import io.github.kapilchoudhary.movieticketbookingappjava.model.Movie;
import io.github.kapilchoudhary.movieticketbookingappjava.model.Screen;
import io.github.kapilchoudhary.movieticketbookingappjava.model.Show;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ShowServiceTest {

    /**
     * PURE UNIT TEST
     *
     * Reason:
     * - We are testing ONLY ShowService logic.
     * - The dependencies (MovieService, TheatreService) are mocked,
     *   because ShowService *calls* them → they are collaborators.
     * - Models like Movie, Screen, Show are real objects (POJOs)
     *   and safe to instantiate (they contain no business logic).
     * - No DB, no locking, no external interaction.
     */

    private MovieService movieService;
    private TheatreService theatreService;
    private ShowService showService;

    @BeforeEach
    void setup() {
        // Arrange: create mocks for dependencies
        movieService = mock(MovieService.class);
        theatreService = mock(TheatreService.class);

        // Class under test (with mocked dependencies)
        showService = new ShowService(theatreService, movieService);
    }

    @Test
    void createShow_whenMovieAndScreenExist_shouldCreateShowSuccessfully() throws Exception {
        // Arrange
        Movie movie = new Movie("MOVIE-1", "Inception", 150);
        Screen screen = new Screen("SCREEN-1", "Screen1");

        when(movieService.getMovieById("MOVIE-1")).thenReturn(movie);
        when(theatreService.getScreenById("SCREEN-1")).thenReturn(screen);

        LocalDateTime startTime = LocalDateTime.now();

        // Act
        String showId = showService.createShow("MOVIE-1", "SCREEN-1", startTime, 150);
        Show createdShow = showService.getShowById(showId);

        // Assert
        assertNotNull(showId);
        assertEquals(movie, createdShow.getMovie());
        assertEquals(screen, createdShow.getScreen());
        assertEquals(startTime, createdShow.getStartTime());
        assertEquals(150, createdShow.getDurationInMinutes());

        // Verify interactions with mocked dependencies (best practice)
        verify(movieService, times(1)).getMovieById("MOVIE-1");
        verify(theatreService, times(1)).getScreenById("SCREEN-1");
    }

    @Test
    void getShowById_whenShowDoesNotExist_shouldThrowException() {
        // Arrange
        String showId = "SHOW-ID-1";

        // Act + Assert
        Exception ex = assertThrows(Exception.class, () -> {
           showService.getShowById(showId);
        });

        assertTrue(ex.getMessage().contains("not found"));
    }
}
