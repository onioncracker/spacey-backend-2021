package com.heroku.spacey.entity;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Data
public class Token {
    private static final int EXPIRATION = 60 * 24;

    private Long tokenId;
    private String token;
    private Date date;

    private Date calculateExpireDate(int expireTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expireTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    public void updateToken(final String token) {
        this.token = token;
        this.date = calculateExpireDate(EXPIRATION);
    }
}
