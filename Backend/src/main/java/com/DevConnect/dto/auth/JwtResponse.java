package com.DevConnect.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "JWT authentication response")
public record JwtResponse(
        @Schema(
                description = "JWT access token",
                example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkZXZlc2gifQ.signature"
        )
        String token,
        @Schema(example = "1")
        Integer userId,
        @Schema(example = "Devesh")
        String username,
        @Schema(example = "devesh@gmail.com")
        String email) {
}
