package com.APIGateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MovieBookingApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieBookingApiGatewayApplication.class, args);
	}

}
