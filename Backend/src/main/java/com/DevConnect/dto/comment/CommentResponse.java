package com.DevConnect.dto.comment;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Represents a comment returned by the API")
public record CommentResponse(
        @Schema(description = "Unique identifier of the comment", example = "12")
        Integer commentId,
        @Schema(description = "Username of the comment author", example = "devesh")
        String username,
        @Schema(description = "Comment content", example = "I solved this using Spring Security.")
        String content,
        @Schema(
                description = "Timestamp when the comment was created",
                example = "2026-07-15T16:30:45"
        )
        LocalDateTime createdAt,
        @Schema(
                description = "Timestamp when the comment was last updated",
                example = "2026-07-15T16:35:45"
        )
        LocalDateTime updatedAt,
        @Schema(description = "Number of upvotes", example = "7")
        Long upVotes,
        @Schema(description = "Number of downvotes", example = "1")
        Long downVotes,
        @Schema(description = "Number of replies", example = "4")
        Long replyCount) {
}
