package com.heroku.spacey.controllers;

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
import com.heroku.spacey.services.impl.UserServiceImpl;
import com.heroku.spacey.utils.EmailComposer;
import com.heroku.spacey.utils.Status;
import com.heroku.spacey.utils.registration.OnRegistrationCompleteEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class AuthController {
    private final UserService userService;
    private final EmailService mailService;
    private final TokenService tokenService;
    private final PasswordService passwordService;
    private final ApplicationEventPublisher eventPublisher;

    public AuthController(UserServiceImpl userService, TokenService tokenService,
                          PasswordService passwordService, EmailService mailService,
                          ApplicationEventPublisher eventPublisher) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.passwordService = passwordService;
        this.mailService = mailService;
        this.eventPublisher = eventPublisher;
    }

    @PostMapping("/register")
    public ResponseEntity userRegistration(@RequestBody @Validated UserRegisterDto registerDto) {
        EmailComposer emailComposer = new EmailComposer("Confirm registration",
                "registration_confirm?token=",
                "http://localhost:8080");
        if (userService.userExists(registerDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("user with such email already exists");
        }
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(userService.registerUser(registerDto), emailComposer));
        String token = userService.generateAuthenticationToken(registerDto.getEmail(), registerDto.getPassword());
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, token)
                .body(new UserTokenDto(token));
    }

    @PostMapping("/login")
    public ResponseEntity<UserTokenDto> login(@RequestBody LoginDto loginDto) {
        String token = userService.generateAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, token)
                .body(new UserTokenDto(token));
    }

    @GetMapping("/registration_confirm")
    public String confirmRegistration(@RequestParam("token") String token) {
        userService.confirmUserRegistration(token);
        return "successfully";
    }

    @PostMapping("/reset_password")
    public String resetPassword(@RequestParam("email") String userEmail) {
        EmailComposer emailComposer = new EmailComposer("Confirm registration", "registration_confirm?token=","http://localhost:8080");
        User user = userService.getUserByEmail(userEmail);
        if (user == null) {
            throw new UserNotFoundException("User email not found!");
        }
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, emailComposer));
        return "resetPasswordEmail";
    }

    @GetMapping("/change_password")
    public String showChangePasswordPage(@RequestParam("token") String token) {
        String result = passwordService.validatePasswordResetToken(token);
        if (result == null) {
            return "redirect:/updatePassword.html?lang=";
        }
        return "redirect:/login.html?lang=";
    }

    @PostMapping("/save_password")
    public String savePassword(@Validated PasswordDto passwordDto) {
        String result = passwordService.validatePasswordResetToken(passwordDto.getToken());
        if (result != null) {
            return "Bad";
        }
        User user = userService.getUserByToken(passwordDto.getToken());
        if (user != null) {
            passwordService.changeUserPassword(user, passwordDto.getNewPassword());
            return "Ok";
        }
        return "Invalid";
    }

    @GetMapping("/resend_registration_token")
    public String resendRegistrationToken(@RequestParam("token") String existingToken) {
        Token newToken = tokenService.generateNewVerificationToken(existingToken);
        EmailComposer emailComposer = new EmailComposer("Confirm registration", "registration_confirm?token=","http://localhost:8080");

        User user = userService.getUserByToken(newToken.getConfirmationToken());


        return "message.resendToken";
    }
}
