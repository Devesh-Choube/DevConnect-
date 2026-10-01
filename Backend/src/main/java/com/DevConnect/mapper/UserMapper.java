package com.DevConnect.mapper;


import com.DevConnect.dto.auth.RegisterRequest;
import com.DevConnect.dto.profile.UserProfileRequest;
import com.DevConnect.dto.profile.UserProfileResponse;
import com.DevConnect.model.User;
import org.springframework.stereotype.Component;
@Component
public class UserMapper {

    public User toEntity(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.username());
        user.setEmail(registerRequest.email());
        return user;
    }

    public void updateEntity(User user, UserProfileRequest request) {
        if (request.bio() != null) {
            user.setBio(request.bio());
        }

        if (request.githubUrl() != null) {
            user.setGithubUrl(request.githubUrl());
        }

        if (request.linkedinUrl() != null) {
            user.setLinkedinUrl(request.linkedinUrl());
        }

        if (request.profileImageUrl() != null) {
            user.setProfileImageUrl(request.profileImageUrl());
        }
    }

    public UserProfileResponse toResponse(User user) {
        return new UserProfileResponse(
                user.getUserId(),
                user.isDeleted() ? "Anonymous" : user.getUsername(),
                user.getBio(),
                user.getProfileImageUrl(),
                user.getGithubUrl(),
                user.getLinkedinUrl(),
                user.getJoinedAt(),
                (long) user.getPosts().size(),
                (long) user.getComments().size()
        );
    }
}