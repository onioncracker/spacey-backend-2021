package com.heroku.spacey.dto;

import lombok.Data;

@Data
public class UserRegisterDto {

    private String email;

    private String password;

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    // TODO extend with other registration fields
}
