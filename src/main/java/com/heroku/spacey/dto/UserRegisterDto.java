package com.heroku.spacey.dto;

import com.heroku.spacey.utils.EmailConstraint;
import com.heroku.spacey.utils.PasswordConstraint;
import lombok.Data;

@Data
public class UserRegisterDto {

    @EmailConstraint
    private String email;
    @PasswordConstraint
    private String password;
    
    private String name;
    private String surname;

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    // TODO extend with other registration fields
}
