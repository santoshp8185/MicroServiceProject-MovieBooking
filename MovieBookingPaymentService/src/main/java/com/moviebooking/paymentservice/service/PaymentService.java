package com.moviebooking.paymentservice.service;
import com.moviebooking.paymentservice.dto.*;
import com.moviebooking.paymentservice.entity.Payment;
import com.moviebooking.paymentservice.repository.PaymentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
@Service
public class PaymentService {
    private final PaymentRepository repository;
    public PaymentService(PaymentRepository repository) { this.repository = repository; }
    public PaymentResponse create(PaymentRequest request) {
        Payment payment = new Payment();
        payment.setBookingId(request.bookingId());
        payment.setAmount(request.amount());
        payment.setPaymentMethod(request.paymentMethod().trim().toUpperCase());
        payment.setStatus("PENDING");
        return map(repository.save(payment));
    }
    public PaymentResponse getById(Long id) {
        return map(repository.findById(id).orElseThrow(() ->
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment not found: " + id)));
    }
    public List<PaymentResponse> getByBookingId(Long bookingId) {
        return repository.findByBookingIdOrderByCreatedAtDesc(bookingId).stream().map(this::map).toList();
    }
    private PaymentResponse map(Payment p) {
        return new PaymentResponse(p.getPaymentId(), p.getBookingId(), p.getAmount(),
                p.getPaymentMethod(), p.getStatus(), p.getCreatedAt());
    }
}
