package com.DevConnect.service;

import com.DevConnect.dto.profile.FollowUserResponse;
import com.DevConnect.exception.DuplicateResourceException;
import com.DevConnect.exception.InvalidRequestException;
import com.DevConnect.model.Follow;
import com.DevConnect.model.User;
import com.DevConnect.repository.FollowRepo;
import com.DevConnect.repository.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepo followRepo;
    private final UserRepo userRepo;
    private final CurrentUserService currentUserService;

    @Transactional
    public void followUser(String username) {

        User follower = currentUserService.getCurrentUser();

        User following = userRepo.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (follower.getUserId().equals(following.getUserId())) {
            throw new InvalidRequestException("You cannot follow yourself");
        }

        if (follower.isDeleted() || following.isDeleted()) {
            throw new InvalidRequestException("Deleted users cannot participate in follows");
        }

        if (followRepo.existsByFollowerAndFollowing(follower, following)) {
            throw new DuplicateResourceException("You are already following this user");
        }

        Follow follow = new Follow();
        follow.setFollower(follower);
        follow.setFollowing(following);

        followRepo.save(follow);
    }

    @Transactional
    public void unfollowUser(String username) {

        User follower = currentUserService.getCurrentUser();

        User following = userRepo.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Follow follow = followRepo.findByFollowerAndFollowing(
                follower,
                following
        ).orElseThrow(() ->
                new EntityNotFoundException(
                        "You are not following this user"
                )
        );

        followRepo.delete(follow);
    }


    public List<FollowUserResponse> getFollowers(String username) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        return followRepo.findByFollowing(user)
                .stream()
                .map(follow -> {
                    User follower = follow.getFollower();

                    return new FollowUserResponse(
                            follower.getUserId(),
                            follower.getUsername()
                    );
                })
                .toList();
    }

    public List<FollowUserResponse> getFollowing(String username) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        return followRepo.findByFollower(user)
                .stream()
                .map(follow -> {
                    User following = follow.getFollowing();

                    return new FollowUserResponse(
                            following.getUserId(),
                            following.getUsername()
                    );
                })
                .toList();
    }

    public Long getFollowingCount(String username) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return followRepo.countByFollower(user);
    }

    public Long getFollowerCount(String username) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return followRepo.countByFollowing(user);
    }
}