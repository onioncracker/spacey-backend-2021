package com.heroku.spacey.dto.user;

import com.heroku.spacey.utils.validators.EmailConstraint;
import com.heroku.spacey.utils.validators.PasswordConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRegisterDto {

    @EmailConstraint
    private String email;
    @PasswordConstraint
    private String password;

    private String firstName;
    private String lastName;

}
