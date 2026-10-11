package com.moviebooking.moviebookinguser.controller;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import com.moviebooking.moviebookinguser.dto.BookMovieRequest;
import com.moviebooking.moviebookinguser.dto.UserResponse;
import com.moviebooking.moviebookinguser.entity.User;
import com.moviebooking.moviebookinguser.repository.UserRepository;
import com.moviebooking.moviebookinguser.service.UserService;

import java.util.Map;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

    private final UserService userService;
    private final RestClient bookingRestClient;
    private final UserRepository userRepository;

    @CrossOrigin
    @PostMapping("/addUser")
    public ResponseEntity<UserResponse> createUser(
            @RequestBody User user) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.createUser(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                userService.getUserById(id));
    }

    @GetMapping("/getAllUser")
    public ResponseEntity<Page<UserResponse>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        return ResponseEntity.ok(
                userService.getAllUsers(page, size));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id,
            @RequestBody User user) {

        return ResponseEntity.ok(
                userService.updateUser(id, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(
            @PathVariable Long id) {

        userService.deleteUser(id);

        return ResponseEntity.ok(
                "User deleted successfully");
    }
    
    @PostMapping("/{userId}/bookings")
    public ResponseEntity<String> bookMovie(
            @PathVariable Long userId,
            @RequestBody BookMovieRequest request) {

       

        Map<String, Object> bookingRequest = Map.of(
                "userId", userId,
                "movieId", request.movieId(),
                "numberOfTickets", request.numberOfTickets(),
                "paymentMethod", request.paymentMethod()
        );

        String response = bookingRestClient.post()
                .uri("/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .body(bookingRequest)
                .retrieve()
                .body(String.class);

        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}