package com.DevConnect.service;
import com.DevConnect.model.PrincipalUserDetails;
import com.DevConnect.model.User;
import com.DevConnect.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MyUserDetailsService  implements UserDetailsService {
    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

       User user= userRepo.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User not found"));
       return new PrincipalUserDetails(user);

    }
}
