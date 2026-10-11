package com.moviebooking.moviesbookingservice.movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.moviebooking.moviesbookingservice.movie.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
