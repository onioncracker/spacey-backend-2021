package com.heroku.spacey.dto.user;

import com.heroku.spacey.utils.validators.EmailConstraint;
import com.heroku.spacey.utils.validators.PasswordConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

    @EmailConstraint
    private String email;
    @PasswordConstraint
    private String password;
}
