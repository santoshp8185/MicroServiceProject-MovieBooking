package com.moviebooking.paymentservice.controller;
import com.moviebooking.paymentservice.dto.*;
import com.moviebooking.paymentservice.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
@RestController
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentService service;
    public PaymentController(PaymentService service) { this.service = service; }
    @PostMapping("/doPayment")
    public ResponseEntity<PaymentResponse> create(@Valid @RequestBody PaymentRequest request) {
        PaymentResponse response = service.create(request);
        return ResponseEntity.created(URI.create("/payments/" + response.paymentId())).body(response);
    }
    @GetMapping("/{id}")
    public PaymentResponse getById(@PathVariable Long id) { return service.getById(id); }
    @GetMapping("/booking/{bookingId}")
    public List<PaymentResponse> getByBookingId(@PathVariable Long bookingId) {
        return service.getByBookingId(bookingId);
    }
}
