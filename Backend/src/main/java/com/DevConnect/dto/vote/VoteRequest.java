package com.DevConnect.dto.vote;

import com.DevConnect.model.VoteType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request payload for voting on a post or comment")
public record VoteRequest(
        @Schema(
                description = "Type of vote to cast",
                allowableValues = {"UPVOTE", "DOWNVOTE"},
                example = "UPVOTE")
        @NotNull(message = "VoteType must not be null")
        VoteType voteType) {
}
