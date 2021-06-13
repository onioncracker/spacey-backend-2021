package com.heroku.spacey.services;

import com.heroku.spacey.dto.user.UserRegisterDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {
    @Override
    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;
    UserDetails registerUser(UserRegisterDto userRegisterDto);
    boolean userExists(String email);
    String generateAuthenticationToken(String email, String password);
}
