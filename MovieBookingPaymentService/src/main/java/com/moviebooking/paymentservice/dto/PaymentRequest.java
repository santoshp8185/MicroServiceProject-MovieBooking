package com.moviebooking.paymentservice.dto;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
public record PaymentRequest(
    @NotNull @Positive Long bookingId,
    @NotNull @DecimalMin("0.01") BigDecimal amount,
    @NotBlank String paymentMethod
) {}
