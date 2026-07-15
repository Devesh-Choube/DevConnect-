package com.DevConnect.mapper;


import com.DevConnect.dto.auth.RegisterRequest;
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

}
