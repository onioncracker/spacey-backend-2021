package com.heroku.spacey.services;

public interface EmailService {
    void sendSimpleMessage(String to, String subject, String text);

    void sendSimpleMessageWithTemplate(String to, String subject, String text, String template);
}
