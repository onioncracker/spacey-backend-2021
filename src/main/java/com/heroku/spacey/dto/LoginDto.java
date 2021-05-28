package com.heroku.spacey.dto;

import com.heroku.spacey.utils.EmailConstraint;
import com.heroku.spacey.utils.PasswordConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginDto {

    @EmailConstraint
    private String email;

    @PasswordConstraint
    private String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
