package com.hotelreview.user.config.resilience4j;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class RateLimiterConfiguration {
    @Bean
    public RateLimiterRegistry getRateLimiterRegistry() {
        RateLimiterConfig rateLimiterConfig = RateLimiterConfig.custom()
                .limitRefreshPeriod(Duration.ofSeconds(1))
                .limitForPeriod(10)
                .timeoutDuration(Duration.ofSeconds(2))
                .build();

        return RateLimiterRegistry.of(rateLimiterConfig);
    }

    @Bean
    public RateLimiter getUserRateLimiter(RateLimiterRegistry registry) {
        return registry.rateLimiter("userRateLimiter");
    }
}
