package com.heroku.spacey.services;

import com.heroku.spacey.dto.user.UserRegisterDto;
import com.heroku.spacey.entity.User;
import com.heroku.spacey.entity.Token;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {
    @Override
    User loadUserByUsername(String email) throws UsernameNotFoundException;

    User registerUser(UserRegisterDto userRegisterDto);

    User userExists(String email);

    String generateAuthenticationToken(String email, String password);

    User getUser(String verificationToken);

    void createVerificationToken(User user, String token);

    void updateUserStatus(User user);

    String validatePasswordResetToken(String token);

    Token getVerificationToken(String token);

    Token generateNewVerificationToken(String existingVerificationToken);

    void changeUserPassword(User user, String password);

    void deleteTokenById(Long id);
}
