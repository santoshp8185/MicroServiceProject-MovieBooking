package com.moviebooking.moviesbookingservice.movie.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.moviebooking.moviesbookingservice.movie.dto.MovieResponse;
import com.moviebooking.moviesbookingservice.movie.entity.Movie;
import com.moviebooking.moviesbookingservice.movie.repository.MovieRepository;

@Service
@Transactional
public class MovieService {
    private final MovieRepository repository;

    public MovieService(MovieRepository repository) {
        this.repository = repository;
    }

    public MovieResponse create(Movie movie) {
        movie.setMovieId(null);
        if (movie.getIsActive() == null) movie.setIsActive(true);
        return toResponse(repository.save(movie));
    }

    @Transactional(readOnly = true)
    public MovieResponse getById(Long id) {
        return repository.findById(id).map(this::toResponse)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Movie not found: " + id));
    }

    @Transactional(readOnly = true)
    public List<MovieResponse> getAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public MovieResponse update(Long id, Movie input) {
        Movie movie = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Movie not found: " + id));
        movie.setTitle(input.getTitle());
        movie.setDescription(input.getDescription());
        movie.setGenre(input.getGenre());
        movie.setLanguage(input.getLanguage());
        movie.setDurationMinutes(input.getDurationMinutes());
        movie.setReleaseDate(input.getReleaseDate());
        movie.setRating(input.getRating());
        movie.setPosterUrl(input.getPosterUrl());
        if (input.getIsActive() != null) movie.setIsActive(input.getIsActive());
        return toResponse(repository.save(movie));
    }

    public void delete(Long id) {
        Movie movie = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Movie not found: " + id));
        repository.delete(movie);
    }

    private MovieResponse toResponse(Movie m) {
        return new MovieResponse(m.getMovieId(), m.getTitle(), m.getDescription(),
                m.getGenre(), m.getLanguage(), m.getDurationMinutes(),
                m.getReleaseDate(), m.getRating(), m.getPosterUrl(), m.getIsActive());
    }
}
