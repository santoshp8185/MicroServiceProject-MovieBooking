package com.moviebooking.moviebookinguser.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record BookMovieRequest(
        @NotNull @Positive Long movieId,
        @Min(1) int numberOfTickets,
        @NotBlank String paymentMethod
) {}