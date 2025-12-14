package io.github.kapilchoudhary.ratelimiter.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.TimeUnit;

@Getter
@Setter
@AllArgsConstructor
public class TokenBucketConfig implements RateLimiterConfig {

    private int globalBucketCapacity;
    private int localBucketCapacity;

    private int globalRefillRate;
    private int localRefillRate;

    private TimeUnit globalRefillRateTimeUnit;
    private TimeUnit localRefillRateTimeUnit;
}
