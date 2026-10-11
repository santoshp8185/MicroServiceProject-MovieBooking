package com.moviebooking.moviebookinguser.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

import com.moviebooking.moviebookinguser.dto.BookingRequest;
import com.moviebooking.moviebookinguser.dto.BookingResponse;
import com.moviebooking.moviebookinguser.dto.UserBookingRequest;
import com.moviebooking.moviebookinguser.repository.UserRepository;

@Service
public class UserBookingService {

    private final RestClient movieBookingRestClient;
    private final UserRepository userRepository;

    public UserBookingService(
            @Qualifier("movieBookingRestClient")
            RestClient movieBookingRestClient,
            UserRepository userRepository) {

        this.movieBookingRestClient = movieBookingRestClient;
        this.userRepository = userRepository;
    }

    public BookingResponse bookMovie(
            Long userId,
            UserBookingRequest request) {

        // Step 1: Validate user
        if (!userRepository.existsById(userId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "User not found with ID: " + userId);
        }

        // Step 2: Prepare MovieBooking request
        BookingRequest bookingRequest = new BookingRequest(
                userId,
                request.movieId(),
                request.showTime(),
                request.numberOfTickets()
        );

        // Step 3: Call MovieBooking Service
        BookingResponse response = movieBookingRestClient
                .post()
                .uri("/bookings/bookMovie")
                .contentType(MediaType.APPLICATION_JSON)
                .body(bookingRequest)
                .retrieve()
                .body(BookingResponse.class);

        if (response == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_GATEWAY,
                    "Empty response from MovieBooking Service");
        }

        return response;
    }
}