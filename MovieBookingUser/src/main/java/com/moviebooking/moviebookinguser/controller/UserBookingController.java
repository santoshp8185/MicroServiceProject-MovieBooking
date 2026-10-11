package com.moviebooking.moviebookinguser.controller;


import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.moviebooking.moviebookinguser.dto.BookingResponse;
import com.moviebooking.moviebookinguser.dto.UserBookingRequest;
import com.moviebooking.moviebookinguser.service.UserBookingService;

@RestController
@RequestMapping("/users")
public class UserBookingController {

    private final UserBookingService bookingService;

    public UserBookingController(
            UserBookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/{userId}/bookMovie")
    public ResponseEntity<BookingResponse> bookMovie(
            @PathVariable Long userId,
            @Valid @RequestBody UserBookingRequest request) {

        BookingResponse response =
                bookingService.bookMovie(userId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}