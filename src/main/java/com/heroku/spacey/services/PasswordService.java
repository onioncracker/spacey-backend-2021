package com.heroku.spacey.services;

import com.heroku.spacey.dto.registration.ResetPasswordDto;
import com.heroku.spacey.entity.User;

import java.util.concurrent.TimeoutException;

public interface PasswordService {
    void changeUserPassword(User user, String password);

    String validatePasswordResetToken(String token) throws TimeoutException;

    boolean passwordConformity(String password, String passwordRepeat);

    boolean resetPasswordMatchOldPassword(User user, ResetPasswordDto resetPasswordDto);

    void saveResetPassword(User user, ResetPasswordDto resetPasswordDto);
}
