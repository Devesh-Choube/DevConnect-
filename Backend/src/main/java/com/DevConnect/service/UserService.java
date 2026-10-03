package com.DevConnect.service;

import com.DevConnect.dto.auth.JwtResponse;
import com.DevConnect.dto.auth.LoginRequest;
import com.DevConnect.dto.auth.RegisterRequest;
import com.DevConnect.dto.profile.UserProfileRequest;
import com.DevConnect.dto.profile.UserProfileResponse;
import com.DevConnect.exception.DuplicateResourceException;
import com.DevConnect.mapper.UserMapper;
import com.DevConnect.model.PrincipalUserDetails;
import com.DevConnect.model.User;
import com.DevConnect.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final CurrentUserService currentUserService;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PostVoteRepo postVoteRepo;
    private final CommentVoteRepo commentVoteRepo;
    private final FollowRepo followRepo;


    @Transactional
    public void deleteMyAccount() {
        User user = currentUserService.getCurrentUser();

        postVoteRepo.deleteByUserId(user.getUserId());
        commentVoteRepo.deleteByUserId(user.getUserId());

        followRepo.deleteAllByUserId(user.getUserId());

        user.setDeleted(true);
        userRepo.save(user);
    }

    public void registerUser(RegisterRequest registerRequest) {
        if(userRepo.existsByUsername(registerRequest.username())){
            throw new DuplicateResourceException("Username already exists");
        }
        if(userRepo.existsByEmail(registerRequest.email()))
            throw new DuplicateResourceException("Email already exists");
        User user = userMapper.toEntity(registerRequest);
        user.setPassword(bCryptPasswordEncoder.encode(registerRequest.password()));
        userRepo.save(user);

    }

    public JwtResponse login(LoginRequest loginRequest) {
      Authentication authentication=  authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password())
        );
        PrincipalUserDetails userDetails = (PrincipalUserDetails)authentication.getPrincipal();
        String token =jwtService.generateJwtToken(userDetails.getUsername());
        return new JwtResponse(token,userDetails.getUserId(),userDetails.getUsername(),userDetails.getEmail());
    }

    public UserProfileResponse updateUserProfile(UserProfileRequest userProfileRequest) {
        User user = currentUserService.getCurrentUser();
        userMapper.updateEntity(user,userProfileRequest);
        userRepo.save(user);
        return userMapper.toResponse(user);

    }

    public UserProfileResponse getMyProfile() {
        User user =  currentUserService.getCurrentUser();
        return userMapper.toResponse(user);
    }

    public UserProfileResponse getUserProfile(String username) {
        User user = userRepo.findByUsername(username).orElseThrow(()->new EntityNotFoundException("User not found"));
        return userMapper.toResponse(user);
    }
}

