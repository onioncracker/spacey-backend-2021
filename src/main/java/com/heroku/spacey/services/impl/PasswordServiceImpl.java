package com.heroku.spacey.services.impl;

import com.heroku.spacey.dao.TokenDao;
import com.heroku.spacey.dao.UserDao;
import com.heroku.spacey.entity.Token;
import com.heroku.spacey.entity.User;
import com.heroku.spacey.services.PasswordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Slf4j
@Service
public class PasswordServiceImpl implements PasswordService {
    private final BCryptPasswordEncoder passwordEncoder;
    private UserDao userDao;
    private final TokenDao tokenDao;

    public PasswordServiceImpl(@Autowired BCryptPasswordEncoder passwordEncoder,
                               @Autowired UserDao userDao,
                               @Autowired TokenDao tokenDao) {
        this.passwordEncoder = passwordEncoder;
        this.userDao = userDao;
        this.tokenDao = tokenDao;
    }

    @Override
    public void changeUserPassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        userDao.updateUser(user);
    }

    @Override
    public String validatePasswordResetToken(String token) {
        final Token passToken = tokenDao.findByToken(token);
        return !isTokenFound(passToken) ? "invalidToken" : isTokenExpired(passToken) ? "expired" : null;
    }

    private boolean isTokenFound(Token passToken) {
        return passToken != null;
    }

    private boolean isTokenExpired(Token passToken) {
        final Calendar cal = Calendar.getInstance();
        return passToken.getDate().before(cal.getTime());
    }
}
