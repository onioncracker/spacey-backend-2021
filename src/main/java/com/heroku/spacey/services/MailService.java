package com.heroku.spacey.services;

public interface MailService {
    void sendSimpleMessage(String to, String subject, String text);

    void sendSimpleMessageWithTemplate(String to, String subject, String text);
}
