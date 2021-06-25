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

import java.util.concurrent.TimeoutException;

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

    @Value("${confirm_registration_url}")
    private String confirmRegistrationUrl;
    @Value("${confirm_registration_subject}")
    private String confirmRegistrationSubject;
    @Value("${confirm_registration_endpoint}")
    private String confirmRegistrationEndpoint;
    @Value("${confirm_registration_template}")
    private String confirmRegistrationTemplate;

    @Value("${resend_registration_url}")
    private String resendRegistrationUrl;
    @Value("${resend_registration_subject}")
    private String resendRegistrationSubject;
    @Value("${resend_registration_endpoint}")
    private String resendRegistrationEndpoint;
    @Value("${resend_registration_template}")
    private String resendRegistrationTemplate;

    @Value("${reset_password_url}")
    private String resetPasswordUrl;
    @Value("${reset_password_subject}")
    private String resetPasswordSubject;
    @Value("${reset_password_endpoint}")
    private String resetPasswordEndpoint;
    @Value("${reset_password_template}")
    private String resetPasswordTemplate;

    @ResponseBody
    @PostMapping("/register")
    public ResponseEntity<Void> userRegistration(@RequestBody @Validated UserRegisterDto registerDto) {
        EmailComposer emailComposer = new EmailComposer(
                confirmRegistrationUrl,
                confirmRegistrationSubject,
                confirmRegistrationEndpoint,
                confirmRegistrationTemplate);
        if (userService.userExists(registerDto.getEmail())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(userService.registerUser(registerDto), emailComposer));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<UserTokenDto> login(@RequestBody LoginDto loginDto) {
        String token = userService.generateAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, token).body(new UserTokenDto(token));
    }

    @GetMapping("/registration_confirm")
    public HttpStatus confirmRegistration(@RequestParam("token") String token) throws TimeoutException {
        userService.confirmUserRegistration(token);
        return HttpStatus.OK;
    }

    @GetMapping("/resend_registration_token")
    public HttpStatus resendRegistrationToken(@RequestParam("token") String existingToken) {
        User user = userService.getUserByToken(existingToken);
        Token newToken = tokenService.generateNewVerificationToken(user, existingToken);
        String token = newToken.getConfirmationToken();
        EmailComposer emailComposer = new EmailComposer(
                resendRegistrationUrl,
                resendRegistrationSubject,
                resendRegistrationEndpoint,
                resendRegistrationTemplate);
        String recipient = user.getEmail();
        emailService.sendSimpleMessageWithTemplate(recipient, emailComposer.getSubject(),
                emailComposer.composeUri(token), emailComposer.getTemplate());
        return HttpStatus.OK;
    }

    @PostMapping("/reset_password")
    public HttpStatus resetPassword(@RequestParam("email") String userEmail) {
        EmailComposer emailComposer = new EmailComposer(
                resetPasswordUrl,
                resetPasswordSubject,
                resetPasswordEndpoint,
                resetPasswordTemplate);
        User user = userService.getUserByEmail(userEmail);
        if (user == null) {
            throw new UserNotFoundException("User email not found!");
        }
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, emailComposer));
        return HttpStatus.OK;
    }

    @GetMapping("/confirm_change_password")
    public HttpStatus showChangePasswordPage(@RequestParam("token") String token) throws TimeoutException {
        String result = passwordService.validatePasswordResetToken(token);
        if (result == null) {
            throw new NotFoundException("User email not found!");
        }
        return HttpStatus.OK;
    }

    @PostMapping("/save_password")
    public HttpStatus savePassword(@RequestBody @Validated PasswordDto passwordDto) throws TimeoutException {
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
