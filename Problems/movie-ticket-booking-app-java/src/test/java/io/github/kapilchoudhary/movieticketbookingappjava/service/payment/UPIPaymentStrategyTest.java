package io.github.kapilchoudhary.movieticketbookingappjava.service.payment;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class UPIPaymentStrategyTest {

    /**
     * This is a PURE UNIT TEST.
     *
     * Reason:
     * - We are testing only UPIPaymentStrategy behavior.
     * - No external systems, no database, no dependencies.
     * - No mocking needed because PaymentStrategy has no constructor-injected collaborators.
     * - The test verifies one simple behavior: successful payment processing.
     */

    @Test
    void processPayment_whenCalled_shouldReturnTrue() {
        // Arrange
        PaymentStrategy paymentStrategy = new UPIPaymentStrategy();

        // Act
        boolean result = paymentStrategy.processPayment();

        // Assert
        assertTrue(result);
    }
}
