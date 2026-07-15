package com.DevConnect.controller;

import com.DevConnect.dto.auth.JwtResponse;
import com.DevConnect.dto.auth.LoginRequest;
import com.DevConnect.dto.auth.RegisterRequest;
import com.DevConnect.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "User authentication endpoints")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterRequest registerRequest){
        userService.registerUser(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

    @PostMapping("/login")
    @Operation(summary = "Login and receive JWT token")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(userService.login(loginRequest));
    }


}
