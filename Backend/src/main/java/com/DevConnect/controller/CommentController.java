package com.DevConnect.controller;


import com.DevConnect.dto.comment.CommentResponse;
import com.DevConnect.dto.comment.CreateCommentRequest;
import com.DevConnect.dto.comment.UpdateCommentRequest;

import com.DevConnect.dto.vote.VoteRequest;
import com.DevConnect.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("posts/{postId}/comments")
@Tag(name="Comments",description="Operations related to comments")
@Validated
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
  @PostMapping
  @Operation(summary = "Add a comment")
    public CommentResponse addComment(@Valid @RequestBody CreateCommentRequest createCommentRequest,
                                      @PathVariable @Positive Integer postId) {
      return commentService.addComment(createCommentRequest,postId);
  }

  @PutMapping("/{commentId}")
  @Operation(summary = "Update a comment")
    public CommentResponse editComment(@Valid @RequestBody UpdateCommentRequest updateCommentRequest, @PathVariable Integer postId, @PathVariable Integer commentId) {
      return commentService.editComment(updateCommentRequest,postId,commentId);

  }
  @PutMapping("/{commentId}/vote")
  @Operation(summary = "Vote on a comment")
  public ResponseEntity<String> voteComment(@PathVariable Integer postId, @PathVariable Integer commentId,@Valid @RequestBody VoteRequest voteRequest) {
    return ResponseEntity.ok(commentService.voteComment(postId,commentId,voteRequest));
  }

  @DeleteMapping("/{commentId}")
  @Operation(summary = "Delete a comment")
    public ResponseEntity<String> deleteComment(@PathVariable Integer postId, @PathVariable Integer commentId) {
      return ResponseEntity.ok(commentService.deleteComment(postId,commentId));
  }
  @GetMapping("/{commentId}")
  @Operation(summary = "Get a comment")
    public CommentResponse getComment(@PathVariable Integer postId, @PathVariable Integer commentId) {
      return commentService.getComment(postId,commentId);
  }

  @GetMapping
  @Operation(summary = "Get all comments for a post")
  public ResponseEntity<Page<CommentResponse>> getAllComments(@PathVariable Integer postId,
                                                              @RequestParam(defaultValue = "0") @Min(0) Integer page,
                                                              @RequestParam(defaultValue = "10")@Min(1) @Max(100) Integer size,
                                                              @RequestParam(defaultValue="createdAt") String sortBy,
                                                              @RequestParam(defaultValue = "DESC")Sort.Direction direction) {
    return ResponseEntity.ok(commentService.getAllComments(postId,page,size,sortBy,direction));
  }

}
