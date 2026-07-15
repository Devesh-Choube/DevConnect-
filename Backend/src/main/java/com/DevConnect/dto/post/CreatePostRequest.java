package com.DevConnect.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Request payload for creating a new post")
public record CreatePostRequest(
        @Schema(
                description = "Title of the post",
                example = "How to secure Spring Boot APIs?"
        )
        @NotBlank(message = "Title must not be blank") @Size(message = "Title must be between 3 to 100 characters", min = 3,max=100)
        String title,
        @Schema(
                description = "Detailed content of the post",
                example = "Can someone explain JWT authentication?"
        )
        @NotBlank(message = "Content must not be blank") @Size(message = "Content must be between 10 to 5000 characters", min = 10,max=5000)
        String content) {
}
