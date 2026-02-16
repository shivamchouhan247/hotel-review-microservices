package com.hotelreview.user.config.resilience4j;

import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class RetryConfiguration {
    @Bean
    public RetryRegistry getRetryRegistry() {
        RetryConfig retryConfig = RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.ofMillis(500))
                .build();

        return RetryRegistry.of(retryConfig);
    }

    @Bean
    public Retry getRatingHotelRetry(RetryRegistry registry) {
        Retry ratingHotelRetry = registry.retry("ratingHotelRetry");
        ratingHotelRetry.getEventPublisher()
                .onRetry(event -> {
                    System.out.println(
                            "Retry attempt: "
                                    + event.getNumberOfRetryAttempts()
                                    + " for "
                                    + event.getName()
                    );
                });
        return ratingHotelRetry;
    }
}
