package com.heroku.spacey.services;

import com.heroku.spacey.entity.Token;
import com.heroku.spacey.entity.User;

public interface TokenService {

    void createVerificationToken(User user, String token);

    Token getVerificationToken(String token);

    Token generateNewVerificationToken(String existingVerificationToken);

    void deleteTokenById(Long id);
}
