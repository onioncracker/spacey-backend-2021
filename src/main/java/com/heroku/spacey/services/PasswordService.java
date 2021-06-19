package com.heroku.spacey.services;

import com.heroku.spacey.entity.User;

public interface PasswordService {
    void changeUserPassword(User user, String password);

    String validatePasswordResetToken(String token);
}
