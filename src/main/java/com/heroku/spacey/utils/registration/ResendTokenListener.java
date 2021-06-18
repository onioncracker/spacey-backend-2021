package com.heroku.spacey.utils.registration;

import com.heroku.spacey.entity.User;
import com.heroku.spacey.services.UserService;
import com.heroku.spacey.services.impl.EmailServiceImpl;
import org.springframework.context.ApplicationListener;

import java.util.UUID;

public class ResendTokenListener implements ApplicationListener<OnRegistrationCompleteEvent> {
    private final UserService service;
    private final EmailServiceImpl mailService;

    public ResendTokenListener(UserService service, EmailServiceImpl mailService) {
        this.service = service;
        this.mailService = mailService;
    }

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.resendToken(event);
    }

    private void resendToken(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        service.createVerificationToken(user, token);

        String recipientAddress = user.getEmail();
        String subject = "Resend verification token";
        String confirmationUrl
                = event.getAppUrl() + "/registrationConfirm.html?token=" + token;
        String text = "http://localhost:8080" + confirmationUrl;
        mailService.sendSimpleMessageWithTemplate(recipientAddress, subject, text);
    }
}
