package com.DevConnect.dto.profile;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "User profile information")
public record UserProfileResponse(

        @Schema(
                description = "Unique identifier of the user",
                example = "1"
        )
        Integer userId,

        @Schema(
                description = "Username of the user",
                example = "Devesh"
        )
        String username,

        @Schema(
                description = "Short description about the user",
                example = "Java Backend Developer | Spring Boot | AWS | Docker"
        )
        String bio,

        @Schema(
                description = "URL of the user's profile image",
                example = "https://example.com/profile.jpg"
        )
        String profileImageUrl,

        @Schema(
                description = "GitHub profile URL",
                example = "https://github.com/devesh"
        )
        String githubUrl,

        @Schema(
                description = "LinkedIn profile URL",
                example = "https://www.linkedin.com/in/devesh"
        )
        String linkedinUrl,

        @Schema(
                description = "Date and time when the user joined",
                example = "2026-09-19T10:30:00"
        )
        LocalDateTime joinedAt,

        @Schema(
                description = "Total number of posts created by the user",
                example = "15"
        )
        Long postCount,

        @Schema(
                description = "Total number of comments created by the user",
                example = "42"
        )
        Long commentCount
) {}