package com.heroku.spacey.services;

import com.heroku.spacey.dto.user.UserRegisterDto;
import com.heroku.spacey.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.concurrent.TimeoutException;

public interface UserService extends UserDetailsService {
    @Override
    User loadUserByUsername(String email) throws UsernameNotFoundException;

    User registerUser(UserRegisterDto userRegisterDto);

    boolean userExists(String email);

    User getUserByToken(String verificationToken);

    String generateAuthenticationToken(String email, String password);

    User getUserByEmail(String email);

    void confirmUserRegistration(String token) throws TimeoutException;
}
