package com.heroku.spacey.dto.user;

import lombok.Data;

import java.util.Date;

@Data
public class UserProfileDto {
    private Long userId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Date dateOfBirth;
    private String sex;
    private String email;
    private String city;
    private String street;
    private String house;
    private String apartment;
}
