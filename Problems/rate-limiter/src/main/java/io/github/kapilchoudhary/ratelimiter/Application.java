package io.github.kapilchoudhary.ratelimiter;

import io.github.kapilchoudhary.ratelimiter.model.TokenBucketConfig;
import io.github.kapilchoudhary.ratelimiter.service.RateLimiterService;
import io.github.kapilchoudhary.ratelimiter.strategy.TokenBucketStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Application {

    public static void main(String[] args) throws Exception {

        // Initial config
        TokenBucketConfig initialConfig = new TokenBucketConfig(
                15,
                3,
                5,
                1,
                TimeUnit.SECONDS,
                TimeUnit.SECONDS
        );

        // Strategy
        TokenBucketStrategy tokenBucketStrategy = new TokenBucketStrategy(initialConfig);

        // Executor for requests
        ExecutorService requestExecutor = Executors.newFixedThreadPool(5);

        // RateLimiterService
        RateLimiterService<TokenBucketConfig> rateLimiterService = new RateLimiterService<>(tokenBucketStrategy, requestExecutor);

        System.out.println("\n==== Sending initial burst ====\n");

        // Burst of requests
        sendBurstRequests(rateLimiterService, 10, "user-1");
        sendBurstRequests(rateLimiterService, 10, "user-2");
//        for (int i = 0; i < 10; i++) {
//            rateLimiterService.processRequest("user-1");
//            rateLimiterService.processRequest("user-2");
//        }

        // Wait for refill
        Thread.sleep(3000);

        System.out.println("\n==== After refill ====\n");

        sendBurstRequests(rateLimiterService, 5, "user-1");
//        for (int i = 0; i < 5; i++) {
//            rateLimiterService.processRequest("user-1");
//        }

        // Update configuration dynamically
        System.out.println("\n==== Updating configuration ====\n");

        TokenBucketConfig updatedConfig = new TokenBucketConfig(
                20,
                5,
                10,
                2,
                TimeUnit.SECONDS,
                TimeUnit.SECONDS
        );

        rateLimiterService.updateConfiguration(updatedConfig);

        Thread.sleep(5000);

        System.out.println("\n==== After config update ====\n");

        sendBurstRequests(rateLimiterService, 10, "user-1");
//        for (int i = 0; i < 10; i++) {
//            rateLimiterService.processRequest("user-1");
//        }

        Thread.sleep(2000);
        rateLimiterService.shutdown();

        System.out.println("\n==== Shutdown complete ====");
    }

    private static void sendBurstRequests(RateLimiterService<TokenBucketConfig> rateLimiterService, int count, String rateLimitKey) {
        List<CompletableFuture<Boolean>> futures = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            futures.add(rateLimiterService.processRequest(rateLimitKey));
        }

        CompletableFuture
                .allOf(futures.toArray(new CompletableFuture[0]))
                .join();

        long allowedRequests = futures.stream()
                .map(CompletableFuture::join)
                .filter(x -> x == true)
                .count();

        System.out.printf("Results: %d allowed, %d blocked (total: %d)%n", allowedRequests, count - allowedRequests, count);
        System.out.println();
    }
}
