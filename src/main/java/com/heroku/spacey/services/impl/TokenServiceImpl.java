package com.heroku.spacey.services.impl;

import com.heroku.spacey.dao.TokenDao;
import com.heroku.spacey.dao.UserDao;
import com.heroku.spacey.entity.Token;
import com.heroku.spacey.entity.User;
import com.heroku.spacey.services.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Calendar;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

@Slf4j
@Service
@RequiredArgsConstructor
@PropertySource("classpath:application.properties")
public class TokenServiceImpl implements TokenService {
    private final UserDao userDao;
    private final TokenDao tokenDao;

    @Value("${link_registration_lifetime}")
    private Long registrationLinkLifetime;

    @Override
    public void createVerificationToken(User user, String token) {
        Token verificationToken = new Token();
        verificationToken.setToken(token);
        Long tokenId = tokenDao.insert(verificationToken);
        user.setTokenId(tokenId);
        userDao.updateUser(user);
    }

    @Override
    public Token getVerificationToken(String token) {
        return tokenDao.findByToken(token);
    }

    @Override
    public Token generateNewVerificationToken(User user, String existingVerificationToken) {
        Token token = tokenDao.findByToken(existingVerificationToken);
        if (token == null) {
            throw new NotFoundException("Token not found");
        }
        token.updateToken(UUID.randomUUID().toString());
        Long tokenId = tokenDao.insert(token);
        user.setTokenId(tokenId);
        userDao.updateUser(user);
        return tokenDao.findByTokenId(tokenId);
    }

    @Override
    public void checkTokenExpiration(Token token) throws TimeoutException {
        Calendar cal = Calendar.getInstance();
        if ((token.getDate().getTime() - cal.getTime().getTime()) > registrationLinkLifetime) {
            throw new TimeoutException("Verification token is out of date");
        }
    }

    @Override
    public void deleteTokenById(Long id) {
        tokenDao.delete(id);
    }
}
