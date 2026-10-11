package com.moviebooking.paymentservice.dto;
import java.math.BigDecimal;
import java.time.LocalDateTime;
public record PaymentResponse(Long paymentId, Long bookingId, BigDecimal amount,
                              String paymentMethod, String status, LocalDateTime createdAt) {}
