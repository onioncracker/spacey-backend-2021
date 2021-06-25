package com.heroku.spacey.services;

import com.heroku.spacey.entity.Token;
import com.heroku.spacey.entity.User;

import java.util.concurrent.TimeoutException;

public interface TokenService {

    void createVerificationToken(User user, String token);

    Token getVerificationToken(String token);

    Token generateNewVerificationToken(User user, String existingVerificationToken);

    void checkTokenExpiration(Token token) throws TimeoutException;

    void deleteTokenById(Long id);
}
