package com.moviebooking.moviebookinguser.dto;



import java.time.LocalDateTime;

public record BookingRequest(

        Long userId,
        Long movieId,
        LocalDateTime showTime,
        Integer numberOfTickets

) {}