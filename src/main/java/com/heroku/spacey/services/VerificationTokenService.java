package com.heroku.spacey.services;

import com.heroku.spacey.dto.registration.VerificationTokenDto;
import com.heroku.spacey.dto.user.UserRegisterDto;

public interface VerificationTokenService {
    VerificationTokenDto findByToken(String token);

    VerificationTokenDto findByUser(UserRegisterDto user);
}
