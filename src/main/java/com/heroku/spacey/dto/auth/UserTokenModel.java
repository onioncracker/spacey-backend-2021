package com.heroku.spacey.dto.auth;

import lombok.Data;

@Data
public class UserTokenModel {
    private int id;
    private String token;
    private String userEmail;
}

