package com.heroku.spacey.services;

import com.heroku.spacey.dto.user.UserRegisterDto;
import com.heroku.spacey.dto.auth.UserTokenDto;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    public UserRegisterDto getUserByToken(String token) {
        return new UserRegisterDto("admin", "password");
    }

    public void addUserToken(UserTokenDto userTokenDto) {
    }
}
