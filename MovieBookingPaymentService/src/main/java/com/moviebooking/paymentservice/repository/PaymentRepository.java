package com.moviebooking.paymentservice.repository;
import com.moviebooking.paymentservice.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByBookingIdOrderByCreatedAtDesc(Long bookingId);
}
