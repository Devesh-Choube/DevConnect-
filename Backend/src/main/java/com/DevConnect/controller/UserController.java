package com.DevConnect.controller;

import com.DevConnect.dto.auth.JwtResponse;
import com.DevConnect.dto.auth.LoginRequest;
import com.DevConnect.dto.auth.RegisterRequest;
import com.DevConnect.dto.profile.UserProfileRequest;
import com.DevConnect.dto.profile.UserProfileResponse;
import com.DevConnect.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
@RestController
@Validated
@Tag(name = "Authentication and User Profile",
        description = "Authentication and User Profile endpoints")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/auth/register")
    @Operation(summary = "Register a new user")
    public ResponseEntity<String> registerUser(
            @Valid @RequestBody RegisterRequest registerRequest) {

        userService.registerUser(registerRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("User registered successfully");
    }

    @PostMapping("/auth/login")
    @Operation(summary = "Login and receive JWT token")
    public ResponseEntity<JwtResponse> login(
            @Valid @RequestBody LoginRequest loginRequest) {

        return ResponseEntity.ok(userService.login(loginRequest));
    }

    @PatchMapping("/users/me")
    @Operation(summary = "Update User Profile")
    public ResponseEntity<UserProfileResponse> updateUserProfile(
            @Valid @RequestBody UserProfileRequest userProfileRequest) {

        return ResponseEntity.ok(
                userService.updateUserProfile(userProfileRequest)
        );
    }

    @GetMapping("/users/me")
    @Operation(summary = "Get My User Profile")
    public ResponseEntity<UserProfileResponse> getMyProfile() {
        return ResponseEntity.ok(userService.getMyProfile());
    }

    @GetMapping("/users/{username}")
    @Operation(summary = "Get Public Profile")
    public ResponseEntity<UserProfileResponse> getUserProfileByUsername(
            @PathVariable @NotBlank String username) {

        return ResponseEntity.ok(
                userService.getUserProfile(username)
        );
    }

    @DeleteMapping("/users/me")
    @Operation(summary = "Delete my account")
    public ResponseEntity<Void> deleteMyAccount() {
        userService.deleteMyAccount();
        return ResponseEntity.noContent().build();
    }
}