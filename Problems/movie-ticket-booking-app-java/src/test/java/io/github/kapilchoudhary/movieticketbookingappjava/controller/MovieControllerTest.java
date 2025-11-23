package io.github.kapilchoudhary.movieticketbookingappjava.controller;

import io.github.kapilchoudhary.movieticketbookingappjava.model.Movie;
import io.github.kapilchoudhary.movieticketbookingappjava.service.MovieService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovieControllerTest {

    @Test
    void createMovie_withValidData_shouldSucceed() throws Exception {
        /**
         * This is a CONTROLLER TEST (not a pure unit test).
         *
         * Reason:
         * - We are creating the real MovieService instead of mocking it.
         * - We test the controller together with its actual dependency.
         * - This verifies the flow between Controller → Service.
         *
         * A pure unit test would mock MovieService and test only the controller logic.
         * Here, we intentionally test the controller + real service → integration-style controller test.
         *
         * AAA -->
         * 1. Arrange: Setup real service and controller.
         * 2. Act: Call the controller method.
         * 3. Assert: Verify the output using the real service behavior.
         */

        // Arrange (real dependency, makes this an integration-style controller test)
        MovieService service = new MovieService();
        MovieController controller = new MovieController(service);

        // Act
        String movieId = controller.createMovie("Inception", 150);
        Movie movie = service.getMovieById(movieId);

        // Assert
        assertEquals("Inception", movie.getName());
    }
}
