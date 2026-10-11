package com.moviebooking.moviesbookingservice.movie.dto;

import java.time.LocalDate;

public record MovieResponse(
        Long movieId, String title, String description, String genre,
        String language, Integer durationMinutes, LocalDate releaseDate,
        Double rating, String posterUrl, Boolean isActive) {
}
