package com.DevConnect.dto.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Request payload for creating a comment")
public record CreateCommentRequest(

        @Schema(
                description = "Parent comment ID. Leave null when creating a top-level comment.",
                example = "5",
                nullable = true
        )
        Integer parentCommentId,
          @Schema(
                  description = "Comment content",
                  example = "I had the same issue and solved it using Spring Security."
          )
        @NotBlank(message = "Content must not be blank") @Size(min = 3,max=1000,message = "Content must be between 3 to 1000 characters")
        String content
       ) {
}

