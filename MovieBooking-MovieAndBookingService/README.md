# MovieBooking-MovieAndBookingService

A single Spring Boot microservice for movie catalog and bookings. Payment is intended to be a separate service.

- Java 17
- Spring Boot 4.0.0
- Spring Cloud 2025.1.0 / Eureka Client
- MySQL and Spring Data JPA
- Service port: 8082
- Eureka service ID: `movie-booking-service`

## Database setup

```sql
CREATE DATABASE movie_booking_movie;
```

Defaults in `application.yaml` are username/password `santosh` / `santosh`, based on the current local project settings. Override with `DB_USERNAME` and `DB_PASSWORD` environment variables.

## Import into STS

1. Extract the ZIP.
2. STS: File → Import → Maven → Existing Maven Projects.
3. Select this project's folder and finish.
4. Run `MoviesBookingServiceApplication.java`.

## Movie APIs

Base URL: `http://localhost:8082/movies`

- `POST /movies` — create movie
- `GET /movies` — list movies
- `GET /movies/{id}` — get movie
- `PUT /movies/{id}` — update movie
- `DELETE /movies/{id}` — delete movie

Example body:
```json
{
  "title": "Example Movie",
  "description": "Sample description",
  "genre": "Action",
  "language": "English",
  "durationMinutes": 120,
  "releaseDate": "2026-10-10",
  "rating": 8.5,
  "posterUrl": "https://example.com/poster.jpg",
  "isActive": true
}
```

## Booking APIs

Base URL: `http://localhost:8082/bookings`

- `POST /bookings` — create booking (initial status `PENDING`)
- `GET /bookings/{id}` — get booking
- `GET /bookings/user/{userId}` — list bookings for user
- `PATCH /bookings/{id}/cancel` — cancel a non-confirmed booking

Example booking request:
```json
{
  "userId": 1,
  "movieId": 1,
  "showTime": "2026-10-20T19:30:00",
  "numberOfTickets": 2
}
```

The sample calculates total amount using a fixed demo ticket price of 200.00 per ticket. Replace this with show-specific pricing and availability checks before production. It does not yet reserve seats or communicate with a payment provider.

## Gateway routing

Add these routes to `spring.cloud.gateway.server.webflux.routes` in the API Gateway:

```yaml
- id: movie-booking-service-movies
  uri: lb://movie-booking-service
  predicates:
    - Path=/movies/**

- id: movie-booking-service-bookings
  uri: lb://movie-booking-service
  predicates:
    - Path=/bookings/**
```

If JWT should protect these endpoints, extend your Gateway JWT filter to include both `/movies/**` and `/bookings/**`.

## Payment Service

Create a separate `payment-service` on another port (for example 8083), with its own database/schema and Eureka registration. A robust flow is: booking created as PENDING → payment service processes payment → booking is confirmed only after trusted payment success notification. Do not accept client-supplied payment success as authoritative.
