package com.heroku.spacey.controllers;

import com.amazonaws.services.apigateway.model.NotFoundException;
import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import com.heroku.spacey.dto.auth.UserTokenDto;
import com.heroku.spacey.dto.registration.PasswordDto;
import com.heroku.spacey.dto.registration.ResetPasswordDto;
import com.heroku.spacey.dto.user.LoginDto;
import com.heroku.spacey.dto.user.UserRegisterDto;
import com.heroku.spacey.entity.Token;
import com.heroku.spacey.entity.User;
import com.heroku.spacey.services.*;
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
    private final EmployeeService employeeService;
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
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(userService.registerUser(registerDto),
            emailComposer));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<UserTokenDto> login(@RequestBody LoginDto loginDto) {
        UserTokenDto response = userService.authenticate(loginDto.getEmail(), loginDto.getPassword());
        return ResponseEntity
            .ok()
            .header(HttpHeaders.AUTHORIZATION, response.getAuthToken())
            .body(response);
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
        String token = newToken.getToken();
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

    @GetMapping("/reset_password_confirm")
    public HttpStatus showResetPasswordPage(@RequestParam("token") String token) throws TimeoutException {
        String tokenValidation = passwordService.validatePasswordResetToken(token);
        if (tokenValidation == null) {
            throw new NotFoundException("User token is incorrect!");
        }
        return HttpStatus.OK;
    }


    @GetMapping("/create-password")
    public HttpStatus showCreatePasswordPage(@RequestParam("token") String token) throws TimeoutException {
        String tokenValidation = passwordService.validatePasswordResetToken(token);
        if (tokenValidation == null) {
            throw new com.amazonaws.services.apigateway.model.NotFoundException("User token is incorrect!");
        }
        return HttpStatus.OK;
    }

    @PostMapping("/reset_password_save")
    public HttpStatus saveResetPassword(@RequestBody @Validated ResetPasswordDto resetPasswordDto) {
        User user = userService.getUserByEmail(resetPasswordDto.getEmail());
        if (user == null) {
            throw new NotFoundException("User not found!");
        }
        passwordService.saveResetPassword(user, resetPasswordDto);
        return HttpStatus.OK;
    }

    @PostMapping("/create-password-save")
    public HttpStatus saveCreatePassword(@RequestBody @Validated ResetPasswordDto resetPasswordDto) {
        User user = userService.getUserByEmail(resetPasswordDto.getEmail());
        if (user == null) {
            throw new com.amazonaws.services.apigateway.model.NotFoundException("Employee not found!");
        }
        passwordService.saveCreatePassword(user, resetPasswordDto);
        employeeService.activateEmployee(user);

        return HttpStatus.OK;
    }

    @PostMapping("/change_password_save")
    public HttpStatus saveChangePassword(@RequestBody @Validated PasswordDto passwordDto) {
        User user = userService.getUserByEmail(passwordDto.getEmail());
        if (user == null) {
            throw new NotFoundException("User not found!");
        }
        if (!passwordService.passwordConformity(passwordDto.getNewPassword(), passwordDto.getNewPasswordRepeat())) {
            return HttpStatus.BAD_REQUEST;
        }
        passwordService.changeUserPassword(user, passwordDto.getNewPassword());
        return HttpStatus.OK;
    }
}

