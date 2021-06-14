package com.heroku.spacey.dao;

import com.heroku.spacey.entity.User;
import com.heroku.spacey.entity.VerificationToken;

import java.sql.Date;

public interface VerificationTokenDao {
    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);

    Long insert(VerificationToken verificationToken);

    void deleteAllExpiredSince(Date now);

    void delete(Long id);
}
