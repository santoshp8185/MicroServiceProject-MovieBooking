package com.moviebooking.moviesbookingservice.booking.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.moviebooking.moviesbookingservice.booking.dto.BookingRequest;
import com.moviebooking.moviesbookingservice.booking.dto.BookingResponse;
import com.moviebooking.moviesbookingservice.booking.entity.Booking;
import com.moviebooking.moviesbookingservice.booking.repository.BookingRepository;
import com.moviebooking.moviesbookingservice.movie.entity.Movie;
import com.moviebooking.moviesbookingservice.movie.repository.MovieRepository;

@Service
@Transactional
public class BookingService {
    private final BookingRepository bookingRepository;
    private final MovieRepository movieRepository;

    public BookingService(BookingRepository bookingRepository, MovieRepository movieRepository) {
        this.bookingRepository = bookingRepository;
        this.movieRepository = movieRepository;
    }

    public BookingResponse create(BookingRequest request) {
        Movie movie = movieRepository.findById(request.movieId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Movie not found: " + request.movieId()));

        if (Boolean.FALSE.equals(movie.getIsActive())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Movie is inactive");
        }

        // Demo pricing only: replace with show-specific pricing in a real system.
        BigDecimal ticketPrice = new BigDecimal("200.00");
        BigDecimal total = ticketPrice.multiply(BigDecimal.valueOf(request.numberOfTickets()));

        Booking booking = new Booking();
        booking.setUserId(request.userId());
        booking.setMovieId(request.movieId());
        booking.setShowTime(request.showTime());
        booking.setNumberOfTickets(request.numberOfTickets());
        booking.setTotalAmount(total);
        booking.setStatus("PENDING");

        return toResponse(bookingRepository.save(booking));
    }

    @Transactional(readOnly = true)
    public BookingResponse getById(Long id) {
        return bookingRepository.findById(id).map(this::toResponse)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Booking not found: " + id));
    }

    @Transactional(readOnly = true)
    public List<BookingResponse> getByUser(Long userId) {
        return bookingRepository.findByUserId(userId).stream()
                .map(this::toResponse).toList();
    }

    public BookingResponse cancel(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Booking not found: " + id));
        if ("CONFIRMED".equals(booking.getStatus())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "A confirmed booking cannot be cancelled by this demo endpoint");
        }
        booking.setStatus("CANCELLED");
        return toResponse(bookingRepository.save(booking));
    }

    private BookingResponse toResponse(Booking b) {
        return new BookingResponse(b.getBookingId(), b.getUserId(), b.getMovieId(),
                b.getShowTime(), b.getNumberOfTickets(), b.getTotalAmount(),
                b.getStatus(), b.getCreatedAt());
    }
}
