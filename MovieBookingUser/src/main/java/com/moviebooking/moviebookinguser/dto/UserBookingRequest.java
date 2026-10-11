package com.moviebooking.moviebookinguser.dto;



import java.time.LocalDateTime;
import jakarta.validation.constraints.*;

public record UserBookingRequest(

        @NotNull
        @Positive
        Long movieId,

        @NotNull
        LocalDateTime showTime,

        @NotNull
        @Min(1)
        Integer numberOfTickets

) {}
