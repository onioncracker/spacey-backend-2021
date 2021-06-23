package com.heroku.spacey.controllers;

import com.amazonaws.services.apigateway.model.NotFoundException;
import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import com.heroku.spacey.dto.auth.UserTokenDto;
import com.heroku.spacey.dto.registration.PasswordDto;
import com.heroku.spacey.dto.user.LoginDto;
import com.heroku.spacey.dto.user.UserRegisterDto;
import com.heroku.spacey.entity.Token;
import com.heroku.spacey.entity.User;
import com.heroku.spacey.services.EmailService;
import com.heroku.spacey.services.PasswordService;
import com.heroku.spacey.services.TokenService;
import com.heroku.spacey.services.UserService;
import com.heroku.spacey.utils.EmailComposer;
import com.heroku.spacey.utils.registration.OnRegistrationCompleteEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@PropertySource("classpath:const.properties")
public class AuthController {
    private final UserService userService;
    private final TokenService tokenService;
    private final EmailService emailService;
    private final PasswordService passwordService;
    private final ApplicationEventPublisher eventPublisher;

    @Value("${confirm_registration_subject}")
    private String confirmRegistrationSubject;
    @Value("${confirm_registration_endpoint}")
    private String confirmRegistrationEndpoint;
    @Value("${confirm_registration_url}")
    private String confirmRegistrationUrl;
    @Value("${resend_registration_subject}")
    private String resendRegistrationSubject;
    @Value("${resend_registration_endpoint}")
    private String resendRegistrationEndpoint;
    @Value("${resend_registration_url}")
    private String resendRegistrationUrl;
    @Value("${reset_password_subject}")
    private String resetPasswordSubject;
    @Value("${reset_password_endpoint}")
    private String resetPasswordEndpoint;
    @Value("${reset_password_url}")
    private String resetPasswordUrl;

    @PostMapping("/register")
    public HttpStatus userRegistration(@RequestBody @Validated UserRegisterDto registerDto) {
        log.info("step 1");
        EmailComposer emailComposer = new EmailComposer(
                confirmRegistrationSubject,
                confirmRegistrationEndpoint,
                confirmRegistrationUrl);
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(userService.registerUser(registerDto), emailComposer));
        log.info("step 5");
        return HttpStatus.OK;
    }

    @PostMapping("/login")
    public ResponseEntity<UserTokenDto> login(@RequestBody LoginDto loginDto) {
        String token = userService.generateAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, token).body(new UserTokenDto(token));
    }

    @GetMapping("/registration_confirm")
    public HttpStatus confirmRegistration(@RequestParam("token") String token) {
        userService.confirmUserRegistration(token);
        return HttpStatus.OK;
    }

    @GetMapping("/resend_registration_token")
    public String resendRegistrationToken(@RequestParam("token") String existingToken) throws URISyntaxException {
        Token newToken = tokenService.generateNewVerificationToken(existingToken);
        String token = newToken.toString();
        EmailComposer emailComposer = new EmailComposer(
                resendRegistrationSubject,
                resendRegistrationEndpoint,
                resendRegistrationUrl);
        User user = userService.getUserByToken(newToken.getConfirmationToken());
        String recipient = user.getEmail();
        emailService.sendSimpleMessageWithTemplate(recipient, emailComposer.getSubject(), emailComposer.composeUri(token));
        return "message.resendToken";
    }

    @PostMapping("/reset_password")
    public HttpStatus resetPassword(@RequestParam("email") String userEmail) {
        EmailComposer emailComposer = new EmailComposer(
                resetPasswordSubject,
                resetPasswordEndpoint,
                resetPasswordUrl);
        User user = userService.getUserByEmail(userEmail);
        if (user == null) {
            throw new UserNotFoundException("User email not found!");
        }
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, emailComposer));
        return HttpStatus.OK;
    }

    @GetMapping("/confirm_change_password")
    public HttpStatus showChangePasswordPage(@RequestParam("token") String token) {
        String result = passwordService.validatePasswordResetToken(token);
        if (result == null) {
            throw new NotFoundException("User email not found!");
        }
        return HttpStatus.OK;
    }

    @PostMapping("/save_password")
    public HttpStatus savePassword(@Validated PasswordDto passwordDto) {
        String result = passwordService.validatePasswordResetToken(passwordDto.getToken());
        if (result != null) {
            return HttpStatus.BAD_REQUEST;
        }
        User user = userService.getUserByToken(passwordDto.getToken());
        if (user != null) {
            passwordService.changeUserPassword(user, passwordDto.getNewPassword());
            return HttpStatus.OK;
        }
        return HttpStatus.NOT_FOUND;
    }
}
