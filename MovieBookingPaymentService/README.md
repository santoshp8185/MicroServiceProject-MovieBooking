# MovieBooking Payment Service

Java 17, Spring Boot 3.5.6, Spring Cloud 2025.0.0, MySQL, Eureka. Runs on port 8083.

## Setup
1. Run `database.sql` in MySQL.
2. Update database credentials in `src/main/resources/application.yaml` if needed.
3. Start Eureka Server on port 8761.
4. Import the `PaymentService` directory into STS as **Existing Maven Project**.
5. Run `PaymentServiceApplication.java` or `mvn spring-boot:run`.

## APIs
- `POST http://localhost:8083/payments`
- `GET http://localhost:8083/payments/1`
- `GET http://localhost:8083/payments/booking/1`

POST body:
```json
{"bookingId":1,"amount":500.00,"paymentMethod":"UPI"}
```

This practice service **only records a pending payment**. It does not charge real funds, confirm a transaction, or check the booking exists. Do not treat PENDING as successful. For gateway routing, point `/payments/**` to `lb://payment-service`.
