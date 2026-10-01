package com.DevConnect.dto.profile;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

@Schema(description = "Request payload for updating the authenticated user's profile")
public record UserProfileRequest(

        @Schema(
                description = "URL of the user's profile image",
                example = "https://example.com/profile.jpg"
        )
        @Size(
                max = 500,
                message = "Profile image URL cannot exceed 500 characters"
        )
        String profileImageUrl,

        @Schema(
                description = "GitHub profile URL",
                example = "https://github.com/devesh"
        )
        @Size(
                max = 500,
                message = "GitHub URL cannot exceed 500 characters"
        )
        String githubUrl,

        @Schema(
                description = "LinkedIn profile URL",
                example = "https://www.linkedin.com/in/devesh"
        )
        @Size(
                max = 500,
                message = "LinkedIn URL cannot exceed 500 characters"
        )
        String linkedinUrl,

        @Schema(
                description = "Short description about the user",
                example = "Java Backend Developer | Spring Boot | AWS | Docker"
        )
        @Size(
                max = 500,
                message = "Bio cannot exceed 500 characters"
        )
        String bio

) {}