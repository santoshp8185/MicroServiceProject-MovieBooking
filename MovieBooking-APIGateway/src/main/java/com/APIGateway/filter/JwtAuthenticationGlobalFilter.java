package com.APIGateway.filter;

import com.APIGateway.security.JwtService;

import io.jsonwebtoken.JwtException;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationGlobalFilter
        implements GlobalFilter, Ordered {

    private final JwtService jwtService;

    public JwtAuthenticationGlobalFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange,
            GatewayFilterChain chain) {

        String path = exchange.getRequest()
                .getURI()
                .getPath();

        // Protect only User Service endpoints.
        if (!path.equals("/users") && !path.startsWith("/users/")) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null
                || !authHeader.startsWith("Bearer ")) {
            return unauthorized(exchange);
        }

        String token = authHeader.substring(7).trim();

        if (token.isEmpty()) {
            return unauthorized(exchange);
        }

        try {
            String username =
                    jwtService.validateAndGetSubject(token);

            // Replace any client-supplied identity header.
            var request = exchange.getRequest()
                    .mutate()
                    .headers(headers -> {
                        headers.remove("X-Authenticated-User");
                        headers.add("X-Authenticated-User", username);
                    })
                    .build();

            return chain.filter(
                    exchange.mutate().request(request).build());

        } catch (JwtException | IllegalArgumentException ex) {
            return unauthorized(exchange);
        }
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange) {
        exchange.getResponse()
                .setStatusCode(HttpStatus.UNAUTHORIZED);

        exchange.getResponse()
                .getHeaders()
                .set(HttpHeaders.WWW_AUTHENTICATE, "Bearer");

        return exchange.getResponse().setComplete();
    }

    @Override
    public int getOrder() {
        return -100;
    }
}