package com.moviebooking.moviebookinguser.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean("movieBookingRestClient")
    public RestClient movieBookingRestClient(
            RestClient.Builder builder,
            @Value("${moviebooking.service.url}") String url) {

        return builder
                .baseUrl(url)
                .build();
    }
}