package com.DevConnect.controller;

import com.DevConnect.dto.profile.FollowUserResponse;
import com.DevConnect.service.FollowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Follow", description = "Follow and unfollow users")
public class FollowController {

    private final FollowService followService;

    @PostMapping("/{username}/follow")
    @Operation(summary = "Follow a user")
    public ResponseEntity<Void> followUser(
            @PathVariable String username) {

        followService.followUser(username);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{username}/follow")
    @Operation(summary = "Unfollow a user")
    public ResponseEntity<Void> unfollowUser(
            @PathVariable String username) {

        followService.unfollowUser(username);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{username}/followers")
    @Operation(summary = "Get followers data")
    public ResponseEntity<List<FollowUserResponse>> getFollowers(
            @PathVariable String username) {

        return ResponseEntity.ok(
                followService.getFollowers(username)
        );
    }

    @GetMapping("/{username}/following")
    @Operation(summary = "Get following data")
    public ResponseEntity<List<FollowUserResponse>> getFollowing(
            @PathVariable String username) {

        return ResponseEntity.ok(
                followService.getFollowing(username)
        );
    }

    @GetMapping("/{username}/followers/count")
    @Operation(summary = "Get follower count")
    public ResponseEntity<Long> getFollowerCount(
            @PathVariable String username) {

        return ResponseEntity.ok(
                followService.getFollowerCount(username)
        );
    }

    @GetMapping("/{username}/following/count")
    @Operation(summary = "Get following count")
    public ResponseEntity<Long> getFollowingCount(
            @PathVariable String username) {

        return ResponseEntity.ok(
                followService.getFollowingCount(username)
        );
    }
}