package com.DevConnect.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Request payload for user registration")
public record RegisterRequest(

        @Schema(example = "Devesh")
        @NotBlank(message = "Username must not be blank")
        @Size(
                min = 3,
                max = 20,
                message = "Username must be between 3 to 20 characters"
        )
        String username,

        @Schema(example = "devesh@gmail.com")
        @NotBlank(message = "Email must not be blank")
        @Email(message = "Enter a valid email")
        @Size(
                max = 254,
                message = "Email cannot exceed 254 characters"
        )
        String email,

        @Schema(
                example = "Password@123",
                format = "password"
        )
        @NotBlank(message = "Password must not be blank")
        @Size(
                min = 8,
                max = 100,
                message = "Password must be between 8 to 100 characters"
        )
        String password
) {}