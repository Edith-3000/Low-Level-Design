package io.github.kapilchoudhary.ratelimiter.service;

import io.github.kapilchoudhary.ratelimiter.model.RateLimiterConfig;
import io.github.kapilchoudhary.ratelimiter.strategy.RateLimiter;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class RateLimiterService<C extends RateLimiterConfig> {

    @Getter
    @Setter
    private RateLimiter<C> rateLimiterStrategy;

    @Getter
    @Setter
    private ExecutorService executorService;

    public RateLimiterService(
            @NonNull final RateLimiter<C> rateLimiterStrategy,
            @NonNull final ExecutorService executorService
    ) {
        this.rateLimiterStrategy = rateLimiterStrategy;
        this.executorService = executorService;
    }

    public CompletableFuture<Boolean> processRequest(@NonNull String rateLimitKey) {
        return CompletableFuture.supplyAsync(() -> {
            boolean allowRequest = rateLimiterStrategy.allowRequest(rateLimitKey);

            if (allowRequest) {
                System.out.printf("Request with key [%s]: ✅ Allowed%n", rateLimitKey);
            } else {
                System.out.printf("Request with key [%s]: ❌ Blocked%n", rateLimitKey);
            }

            return allowRequest;
        }, executorService);
    }

    public void updateConfiguration(@NonNull final C rateLimiterConfig) {
        rateLimiterStrategy.updateConfiguration(rateLimiterConfig);
    }

    public void shutdown() {
        rateLimiterStrategy.shutdown();

        executorService.shutdown();

        try {
            if (!executorService.awaitTermination(30, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
