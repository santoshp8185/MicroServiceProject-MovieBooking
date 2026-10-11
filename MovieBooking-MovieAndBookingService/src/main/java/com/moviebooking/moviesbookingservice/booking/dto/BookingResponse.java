package com.moviebooking.moviesbookingservice.booking.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BookingResponse(
        Long bookingId, Long userId, Long movieId, LocalDateTime showTime,
        Integer numberOfTickets, BigDecimal totalAmount, String status,
        LocalDateTime createdAt) {
}
