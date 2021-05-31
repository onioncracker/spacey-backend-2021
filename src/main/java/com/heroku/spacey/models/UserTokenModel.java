package com.heroku.spacey.models;

import lombok.Data;

@Data
public class UserTokenModel {
    private int id;
    private String token;
    private String userEmail;
}

