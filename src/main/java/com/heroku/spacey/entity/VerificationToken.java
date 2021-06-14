package com.heroku.spacey.entity;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Calendar;

@Data
public class VerificationToken {
    private Long tokenId;
    private String confirmationToken;
    private Timestamp date;

    private Timestamp calculateExpireDate(int expireTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expireTimeInMinutes);
        return new Timestamp(cal.getTime().getTime());
    }
}
