package com.DevConnect.service;

import com.DevConnect.exception.UnauthorizedUserException;
import com.DevConnect.model.PrincipalUserDetails;
import com.DevConnect.model.User;
import com.DevConnect.repository.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CurrentUserService {
    private final UserRepo userRepo;


    private PrincipalUserDetails getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !(authentication.getPrincipal() instanceof PrincipalUserDetails principal)) {
            throw new UnauthorizedUserException("User is not authenticated");
        }

        return principal;
    }

    public User getCurrentUser() {

        return userRepo.findById(getPrincipal().getUserId()).orElseThrow(()->new EntityNotFoundException("User not found"));
    }

    public Integer getCurrentUserId() {
        return getPrincipal().getUserId();
    }

    public void validateOwnership(Integer ownerId)
    {
        if(!ownerId.equals(getCurrentUserId()))
        {
            throw new UnauthorizedUserException("Unauthorized User");
        }
    }
}
