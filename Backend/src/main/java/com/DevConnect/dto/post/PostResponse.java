package com.DevConnect.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Represents a post returned by the API")
public record PostResponse(
        @Schema(description = "Unique identifier of the post", example = "1")
        Integer postId,
        @Schema(description = "Username of the post author", example = "devesh")
        String username,
        @Schema(description = "Post title", example = "How to secure Spring Boot APIs?")
        String title,
        @Schema(description = "Post content", example = "Can someone explain JWT authentication?")
        String content,
        @Schema(
                description = "Timestamp when the post was created",
                example = "2026-07-15T16:30:45"
        )

        LocalDateTime createdAt,
        @Schema(
                description = "Timestamp when the post was last updated",
                example = "2026-07-15T16:35:45"
        )

        LocalDateTime updatedAt,
        @Schema(description = "Number of upvotes", example = "15")
        Long upVotes,
        @Schema(description = "Number of downvotes", example = "2")
        Long downVotes,
        @Schema(description = "Number of comments", example = "8")
        Long commentCount) {
}
