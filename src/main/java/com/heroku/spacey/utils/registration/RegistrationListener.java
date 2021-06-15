package com.heroku.spacey.utils.registration;

import com.heroku.spacey.entity.User;
import com.heroku.spacey.services.UserService;
import com.heroku.spacey.services.impl.EmailServiceImpl;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {
    private final UserService service;
    private final EmailServiceImpl mailService;

    public RegistrationListener(UserService service, EmailServiceImpl mailService) {
        this.service = service;
        this.mailService = mailService;
    }

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent onRegistrationCompleteEvent) {
        this.confirmRegistration(onRegistrationCompleteEvent);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        service.createVerificationToken(user, token);

        String recipientAddress = user.getEmail();
        String subject = "Registration Confirmation";
        String confirmationUrl
                = event.getAppUrl() + "/registrationConfirm.html?token=" + token;
        mailService.sendSimpleMessageWithTemplate(recipientAddress, subject, confirmationUrl);
    }

    private void recoverPassword(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        service.createVerificationToken(user, token);

        String recipientAddress = user.getEmail();
        String subject = "Recover password";
        String confirmationUrl
                = event.getAppUrl() + "/recoverPassword.html?token=" + token;
        mailService.sendSimpleMessageWithTemplate(recipientAddress, subject, confirmationUrl);
    }
}
