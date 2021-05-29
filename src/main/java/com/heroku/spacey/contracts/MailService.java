package com.heroku.spacey.contracts;

public interface MailService {
    void sendSimpleMessage(String to, String subject, String text);

    void sendSimpleMessageWithTemplate(String to, String subject, String text);
}
