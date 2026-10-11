package com.moviebooking.moviesbookingservice.booking.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record BookingRequest(
        @NotNull Long userId,
        @NotNull Long movieId,
        @NotNull LocalDateTime showTime,
        @NotNull @Min(1) Integer numberOfTickets) {
}
