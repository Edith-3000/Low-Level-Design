package io.github.kapilchoudhary.ratelimiter.strategy;

import io.github.kapilchoudhary.ratelimiter.model.RateLimiterConfig;
import lombok.NonNull;

public interface RateLimiter<C extends RateLimiterConfig> {
    public abstract boolean allowRequest(@NonNull final String rateLimitKey);
    public abstract void updateConfiguration(C config);
    public abstract void shutdown();
}
