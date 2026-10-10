
package com.APIGateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import io.github.resilience4j.ratelimiter.RateLimiter;

@Component
public class UserRateLimitFilter
        extends AbstractGatewayFilterFactory<UserRateLimitFilter.Config> {

    private final RateLimiter rateLimiter;

    public UserRateLimitFilter(RateLimiter rateLimiter) {
        super(Config.class);
        this.rateLimiter = rateLimiter;
    }

    @Override
    public String name() {
        return "UserRateLimit";
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (!rateLimiter.acquirePermission()) {
                exchange.getResponse()
                        .setStatusCode(HttpStatus.TOO_MANY_REQUESTS);

                return exchange.getResponse().setComplete();
            }

            return chain.filter(exchange);
        };
    }

    public static class Config {
    }
}