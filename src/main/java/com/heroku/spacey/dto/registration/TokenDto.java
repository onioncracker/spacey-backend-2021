package com.heroku.spacey.dto.registration;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class TokenDto {
    private Long tokenId;
    private String confirmationToken;
    private Timestamp date;
}
