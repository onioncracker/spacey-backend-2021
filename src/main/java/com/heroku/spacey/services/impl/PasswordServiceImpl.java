package com.heroku.spacey.services.impl;

import com.heroku.spacey.dao.TokenDao;
import com.heroku.spacey.dao.UserDao;
import com.heroku.spacey.entity.Token;
import com.heroku.spacey.entity.User;
import com.heroku.spacey.services.PasswordService;
import com.heroku.spacey.services.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.concurrent.TimeoutException;

@Slf4j
@Service
public class PasswordServiceImpl implements PasswordService {
    private final BCryptPasswordEncoder passwordEncoder;
    private UserDao userDao;
    private final TokenService tokenService;
    private final TokenDao tokenDao;

    public PasswordServiceImpl(@Autowired BCryptPasswordEncoder passwordEncoder,
                               @Autowired UserDao userDao,
                               TokenService tokenService, @Autowired TokenDao tokenDao) {
        this.passwordEncoder = passwordEncoder;
        this.userDao = userDao;
        this.tokenService = tokenService;
        this.tokenDao = tokenDao;
    }

    @Override
    public void changeUserPassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        userDao.updateUser(user);
    }

    @Override
    public String validatePasswordResetToken(String token) throws TimeoutException {
        final Token passToken = tokenDao.findByToken(token);
        tokenService.checkTokenExpiration(passToken);
        if (!isTokenFound(passToken)) {
            return "invalidToken";
        }
        return null;
    }

    private boolean isTokenFound(Token passToken) {
        return passToken != null;
    }
}
