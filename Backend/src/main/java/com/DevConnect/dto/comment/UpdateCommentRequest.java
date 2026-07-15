package com.DevConnect.dto.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Request payload for updating a comment")
public record UpdateCommentRequest(
        @Schema(
                description = "Comment content",
                example = "I had the same issue and solved it using Spring Security."
        )
        @NotBlank(message = "Content must not be blank") @Size(message = "Content must be between 3 to 1000 characters", min = 3,max=1000)
        String content) {
}
