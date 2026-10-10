
package com.APIGateway.controller;

import com.APIGateway.security.JwtService;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;

    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/token")
    public ResponseEntity<Map<String, String>> createToken(
            @RequestBody Map<String, String> request) {

        String username = request.get("username");

        if (username == null || username.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of(
                            "error", "Username is required"));
        }

        // DEMO ONLY: Verify credentials before issuing tokens
        // in a real application.
        String token = jwtService.generateToken(username);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("token", token));
    }
}