package com.heroku.spacey.utils.registration;

import com.heroku.spacey.entity.User;
import com.heroku.spacey.services.UserService;
import com.heroku.spacey.services.impl.EmailServiceImpl;
import org.springframework.context.ApplicationListener;

import java.util.UUID;

public class RecoverPasswordListener implements ApplicationListener<OnRegistrationCompleteEvent> {
    private final UserService service;
    private final EmailServiceImpl mailService;

    public RecoverPasswordListener(UserService service, EmailServiceImpl mailService) {
        this.service = service;
        this.mailService = mailService;
    }

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.recoverPassword(event);
    }

    private void recoverPassword(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        service.createVerificationToken(user, token);

        String recipientAddress = user.getEmail();
        String subject = "Recover password";
        String confirmationUrl
                = event.getAppUrl() + "/resetPassword.html?token=" + token;
        String text = "http://localhost:8080" + confirmationUrl;
        mailService.sendSimpleMessageWithTemplate(recipientAddress, subject, text);
    }
}
