package com.heroku.spacey.services.impl;

import com.heroku.spacey.dao.TokenDao;
import com.heroku.spacey.dao.UserDao;
import com.heroku.spacey.dto.registration.ResetPasswordDto;
import com.heroku.spacey.entity.Token;
import com.heroku.spacey.entity.User;
import com.heroku.spacey.services.PasswordService;
import com.heroku.spacey.services.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.InputMismatchException;
import java.util.concurrent.TimeoutException;

@Slf4j
@Service
@RequiredArgsConstructor
public class PasswordServiceImpl implements PasswordService {
    private final UserDao userDao;
    private final TokenDao tokenDao;
    private final TokenService tokenService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void changeUserPassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        userDao.updateUser(user);
    }

    @Override
    public String validatePasswordResetToken(String token) throws TimeoutException {
        Token passToken = tokenDao.findByToken(token);
        tokenService.checkTokenExpiration(passToken);
        if (passToken == null) {
            return null;
        }
        return "token accepted";
    }

    @Override
    public boolean passwordConformity(String password, String passwordRepeat) {
        return password.equals(passwordRepeat);
    }

    @Override
    public boolean resetPasswordMatchOldPassword(User user, ResetPasswordDto resetPasswordDto) {
        return user.getPassword().equals(resetPasswordDto.getPassword());
    }

    @Override
    public void saveResetPassword(User user, ResetPasswordDto resetPasswordDto) {
        if (!passwordConformity(resetPasswordDto.getPassword(), resetPasswordDto.getPasswordRepeat())) {
            throw new InputMismatchException("Passwords mismatch");
        }
        if (!resetPasswordMatchOldPassword(user, resetPasswordDto)) {
            throw new InputMismatchException("New password match old password");
        }
        changeUserPassword(user, resetPasswordDto.getPassword());
    }

    @Override
    public void saveCreatePassword(User user, ResetPasswordDto resetPasswordDto) {
        if (!passwordConformity(resetPasswordDto.getPassword(), resetPasswordDto.getPasswordRepeat())) {
            throw new InputMismatchException("Passwords mismatch");
        }

        changeUserPassword(user, resetPasswordDto.getPassword());
    }
}
