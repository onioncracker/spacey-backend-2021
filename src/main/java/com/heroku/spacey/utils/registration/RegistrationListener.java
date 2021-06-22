package com.heroku.spacey.utils.registration;

import com.heroku.spacey.entity.User;
import com.heroku.spacey.services.EmailService;
import com.heroku.spacey.services.TokenService;
import com.heroku.spacey.services.impl.EmailServiceImpl;
import lombok.SneakyThrows;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {
    private final TokenService tokenService;
    private final EmailService mailService;

    public RegistrationListener(TokenService tokenService, EmailServiceImpl mailService) {
        this.tokenService = tokenService;
        this.mailService = mailService;
    }

    @Override
    @SneakyThrows
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        tokenService.createVerificationToken(user, token);

        String recipientAddress = user.getEmail();
        mailService.sendSimpleMessageWithTemplate(recipientAddress,
                event.getEmailComposer().getSubject(),
                event.getEmailComposer().composeUri(token));
    }
}
