package com.DevConnect.service;

import com.DevConnect.dto.auth.JwtResponse;
import com.DevConnect.dto.auth.LoginRequest;
import com.DevConnect.dto.auth.RegisterRequest;
import com.DevConnect.exception.DuplicateResourceException;
import com.DevConnect.mapper.UserMapper;
import com.DevConnect.model.PrincipalUserDetails;
import com.DevConnect.model.User;
import com.DevConnect.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

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
}
