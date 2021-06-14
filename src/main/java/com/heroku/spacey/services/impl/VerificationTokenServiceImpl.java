package com.heroku.spacey.services.impl;

import com.heroku.spacey.dao.VerificationTokenDao;
import com.heroku.spacey.dto.registration.VerificationTokenDto;
import com.heroku.spacey.dto.user.UserRegisterDto;
import com.heroku.spacey.entity.User;
import com.heroku.spacey.entity.VerificationToken;
import com.heroku.spacey.services.VerificationTokenService;
import com.heroku.spacey.utils.convertors.TokenConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Calendar;

public class VerificationTokenServiceImpl implements VerificationTokenService {
    private VerificationTokenDao verificationTokenDao;
    private final TokenConvertor tokenConvertor;

    public VerificationTokenServiceImpl(@Autowired VerificationTokenDao verificationTokenDao,
                                        @Autowired TokenConvertor tokenConvertor) {
        this.verificationTokenDao = verificationTokenDao;
        this.tokenConvertor = tokenConvertor;
    }

    @Override
    @Transactional
    public VerificationTokenDto findByToken(String token) {
        return null;
    }

    @Override
    @Transactional
    public VerificationTokenDto findByUser(UserRegisterDto user) {
        return null;
    }

    @Transactional
    public void save(User user, String token) {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setDate(calculateExpireDate(24 * 60));

    }

    private Timestamp calculateExpireDate(int expireTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expireTimeInMinutes);
        return new Timestamp(cal.getTime().getTime());
    }
}
