package com.heroku.spacey.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

// TODO Is not used, may be deleted

@Data
@AllArgsConstructor
public class UserTokenDto {
    private String authToken;
}
