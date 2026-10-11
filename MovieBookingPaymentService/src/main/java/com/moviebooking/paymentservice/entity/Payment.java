package com.moviebooking.paymentservice.entity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@Entity
@Table(name = "payments")
public class Payment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id") private Long paymentId;
    @Column(name = "booking_id", nullable = false) private Long bookingId;
    @Column(nullable = false, precision = 10, scale = 2) private BigDecimal amount;
    @Column(name = "payment_method", nullable = false, length = 30) private String paymentMethod;
    @Column(nullable = false, length = 20) private String status = "PENDING";
    @Column(name = "created_at", nullable = false) private LocalDateTime createdAt;
    @PrePersist public void onCreate() { if (createdAt == null) createdAt = LocalDateTime.now(); }
    public Long getPaymentId() { return paymentId; }
    public void setPaymentId(Long v) { paymentId = v; }
    public Long getBookingId() { return bookingId; }
    public void setBookingId(Long v) { bookingId = v; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal v) { amount = v; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String v) { paymentMethod = v; }
    public String getStatus() { return status; }
    public void setStatus(String v) { status = v; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime v) { createdAt = v; }
}
