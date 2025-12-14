package io.github.kapilchoudhary.ratelimiter.strategy;

import io.github.kapilchoudhary.ratelimiter.model.TokenBucketConfig;
import lombok.Getter;
import lombok.NonNull;

import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

@Getter
public class TokenBucketStrategy implements RateLimiter<TokenBucketConfig> {

    private int globalBucketCapacity;
    private int localBucketCapacity;

    private int globalRefillRate;
    private int localRefillRate;

    private TimeUnit globalRefillRateTimeUnit;
    private TimeUnit localRefillRateTimeUnit;

    private final Bucket globalBucket;
    private final Map<String, Bucket> localBuckets;

    private final ScheduledExecutorService scheduler;

    private ScheduledFuture<?> globalRefillFuture;
    private ScheduledFuture<?> localRefillFuture;

    private static class Bucket {
        private int bucketCapacity;
        private int refillRate;
        private int tokens;
        private final ReentrantLock lock;

        private Bucket(
                final int bucketCapacity,
                final int refillRate
        ) {
            this.bucketCapacity = bucketCapacity;
            this.refillRate = refillRate;
            this.tokens = bucketCapacity;
            this.lock = new ReentrantLock();
        }

        private void refill() {
            lock.lock();

            try {
                tokens = Math.min(bucketCapacity, tokens + refillRate);
            } finally {
                lock.unlock();
            }
        }

        private boolean tryConsume() {
            lock.lock();

            try {
                if (tokens > 0) {
                    tokens -= 1;
                    return true;
                } else {
                    return false;
                }
            } finally {
                lock.unlock();
            }
        }

        private void setBucketCapacity(final int bucketCapacity) {
            lock.lock();

            try {
                this.bucketCapacity = bucketCapacity;
            } finally {
                lock.unlock();
            }
        }

        private void setRefillRate(final int refillRate) {
            lock.lock();

            try {
                this.refillRate = refillRate;
            } finally {
                lock.unlock();
            }
        }
    }

    public TokenBucketStrategy(@NonNull final TokenBucketConfig tokenBucketConfig) {
        this.globalBucketCapacity = tokenBucketConfig.getGlobalBucketCapacity();
        this.localBucketCapacity = tokenBucketConfig.getLocalBucketCapacity();

        this.globalRefillRate = tokenBucketConfig.getGlobalRefillRate();
        this.localRefillRate = tokenBucketConfig.getLocalRefillRate();

        this.globalRefillRateTimeUnit = tokenBucketConfig.getGlobalRefillRateTimeUnit();
        this.localRefillRateTimeUnit = tokenBucketConfig.getLocalRefillRateTimeUnit();

        this.globalBucket = new Bucket(globalBucketCapacity, globalRefillRate);
        this.localBuckets = new ConcurrentHashMap<>();

        this.scheduler = Executors.newSingleThreadScheduledExecutor();

        initiateRefillTask();
    }

    private void initiateRefillTask() {
        globalRefillFuture = scheduler.scheduleAtFixedRate(
            () -> globalBucket.refill(),
            globalRefillRate,
            globalRefillRate,
            globalRefillRateTimeUnit
        );

        localRefillFuture = scheduler.scheduleAtFixedRate(
            () -> {
                for (Bucket localBucket: localBuckets.values()) {
                    localBucket.refill();
                }
            },
            localRefillRate,
            localRefillRate,
            localRefillRateTimeUnit
        );
    }

    @Override
    public boolean allowRequest(@NonNull final String rateLimitKey) {
        Bucket localBucket = localBuckets.computeIfAbsent(rateLimitKey, k -> {
           return new Bucket(localBucketCapacity, localRefillRate);
        });

        // TODO: This can be made better: Refer "Strategy-level atomic logic" heading in https://chatgpt.com/share/693d21e2-5364-8001-99ce-bed431dcb72b
        //       This will unnecessarily waste a globalBucket if it is available but localBucket token is not available.
        if (globalBucket.tryConsume() && localBucket.tryConsume()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void updateConfiguration(TokenBucketConfig tokenBucketConfig) {
        this.globalBucketCapacity = tokenBucketConfig.getGlobalBucketCapacity();
        this.localBucketCapacity = tokenBucketConfig.getLocalBucketCapacity();

        this.globalRefillRate = tokenBucketConfig.getGlobalRefillRate();
        this.localRefillRate = tokenBucketConfig.getLocalRefillRate();

        this.globalRefillRateTimeUnit = tokenBucketConfig.getGlobalRefillRateTimeUnit();
        this.localRefillRateTimeUnit = tokenBucketConfig.getLocalRefillRateTimeUnit();

        globalBucket.setBucketCapacity(this.globalBucketCapacity);
        globalBucket.setRefillRate(this.globalRefillRate);

        for (Bucket localBucket: localBuckets.values()) {
            localBucket.setBucketCapacity(this.localBucketCapacity);
            localBucket.setRefillRate(this.localRefillRate);
        }

        globalRefillFuture.cancel(false);
        localRefillFuture.cancel(false);

        initiateRefillTask();
    }

    @Override
    public void shutdown() {
        scheduler.shutdown();

        try {
            if (!scheduler.awaitTermination(10, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
