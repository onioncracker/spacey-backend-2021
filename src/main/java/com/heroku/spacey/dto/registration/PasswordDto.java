package com.heroku.spacey.dto.registration;

import com.heroku.spacey.utils.validators.PasswordConstraint;
import lombok.Data;

@Data
public class PasswordDto {
    private String oldPassword;

    private String token;

    @PasswordConstraint
    private String newPassword;
}
