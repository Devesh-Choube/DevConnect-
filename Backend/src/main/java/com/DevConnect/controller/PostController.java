package com.DevConnect.controller;


import com.DevConnect.dto.post.CreatePostRequest;
import com.DevConnect.dto.post.PostResponse;
import com.DevConnect.dto.post.UpdatePostRequest;
import com.DevConnect.dto.vote.VoteRequest;
import com.DevConnect.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@Validated
@RequestMapping("/posts")
@Tag(name = "Posts", description = "Operations related to posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @Operation(summary = "Create a new post")
    @PostMapping
    public PostResponse createPost(@Valid @RequestBody CreatePostRequest postRequest) {
        return postService.createPost(postRequest);
    }

    @Operation(summary = "Update a post")
    @PutMapping("/{id}")
    public PostResponse updatePost(@Valid @RequestBody UpdatePostRequest updatePostRequest, @PathVariable Integer id) {
        return postService.updatePost(updatePostRequest,id);
    }
    @Operation(summary = "Vote on a post")
    @PutMapping("/{id}/vote")
    public ResponseEntity<String> votePost(@PathVariable Integer id, @Valid @RequestBody VoteRequest voteRequest) {

        return ResponseEntity.ok(postService.votePost(id,voteRequest));
    }

    @Operation(summary = "Delete a post")
    @DeleteMapping("/{id}")
    public String deletePost(@PathVariable Integer id) {
        return postService.deletePost(id);
    }


    @Operation(summary = "Get a post")
    @GetMapping("/{postId}")
    public PostResponse getPost(@PathVariable Integer postId) {
        return postService.getPost(postId);
    }


    @Operation(summary = "Get all posts")
    @GetMapping
    public ResponseEntity<Page<PostResponse>> getAllPosts(@RequestParam(defaultValue = "0") @Min(0) int page,
                                                          @RequestParam(defaultValue="10")@Min(1) @Max(100) int size,
                                                          @RequestParam(defaultValue = "createdAt") String sortBy,
                                                          @RequestParam(defaultValue = "ASC")  Sort.Direction direction
                                                          ) {
        return ResponseEntity.ok(postService.getAllPosts(page,size,sortBy,direction));
    }

    @Operation(summary = "Search posts")
    @GetMapping("/search")
    public ResponseEntity<Page<PostResponse>> searchPosts(@RequestParam @NotBlank String keyword,
                                                          @RequestParam(defaultValue = "0") @Min(0) int page,
                                                          @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size,
                                                          @RequestParam(defaultValue="createdAt") String sortBy,
                                                          @RequestParam(defaultValue = "ASC" )  Sort.Direction direction
                                                           )
    {
        return ResponseEntity.ok(postService.searchPosts(keyword,page,size,sortBy,direction));

    }


}
