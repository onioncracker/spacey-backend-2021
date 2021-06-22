package com.heroku.spacey.dao;

import com.heroku.spacey.entity.User;
import com.heroku.spacey.entity.Token;

import java.sql.Date;

public interface TokenDao {
    Token findByToken(String token);

    Token findByTokenId(Long id);

    Token findByUser(User user);

    Long insert(Token token);

    void deleteAllExpiredSince(Date now);

    void delete(Long id);
}
