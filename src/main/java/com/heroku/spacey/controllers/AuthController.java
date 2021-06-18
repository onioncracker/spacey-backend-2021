package com.heroku.spacey.controllers;

import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import com.heroku.spacey.dto.auth.UserTokenDto;
import com.heroku.spacey.dto.registration.PasswordDto;
import com.heroku.spacey.dto.user.LoginDto;
import com.heroku.spacey.dto.user.UserRegisterDto;
import com.heroku.spacey.entity.Token;
import com.heroku.spacey.entity.User;
import com.heroku.spacey.services.EmailService;
import com.heroku.spacey.services.UserService;
import com.heroku.spacey.services.impl.UserServiceImpl;
import com.heroku.spacey.utils.Status;
import com.heroku.spacey.utils.registration.OnRegistrationCompleteEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class AuthController {
    private final UserService userService;
    private final EmailService mailService;
    private final ApplicationEventPublisher eventPublisher;

    public AuthController(UserServiceImpl userService, EmailService mailService, ApplicationEventPublisher eventPublisher) {
        this.userService = userService;
        this.mailService = mailService;
        this.eventPublisher = eventPublisher;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> userRegistration(@RequestBody @Validated UserRegisterDto registerDto, HttpServletRequest request) {
        if (userService.userExists(registerDto.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("user with such email already exists");
        }
        String appUrl = request.getContextPath();
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(userService.registerUser(registerDto),
                request.getLocale(), appUrl));
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
        Token verificationToken = userService.getVerificationToken(token);
        if (verificationToken == null) {
            return "verification token null";
        }

        User user = userService.getUser(token);
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getDate().getTime() - cal.getTime().getTime()) <= 0) {
            return "verification token is expired";
        }

        user.setStatusId(Status.ACTIVATED.value);
        user.setActivation(true);
        userService.updateUserStatus(user);
        userService.deleteTokenById(verificationToken.getTokenId());

        return "successfully";
    }

    @PostMapping("/reset_password")
    public String resetPassword(@RequestParam("email") String userEmail) {
        User user = userService.userExists(userEmail);
        if (user == null) {
            throw new UserNotFoundException("User email not found!");
        }
        String token = UUID.randomUUID().toString();
        userService.createVerificationToken(user, token);
        mailService.sendSimpleMessageWithTemplate(userEmail, "reset password", "hey");
        return "resetPasswordEmail";
    }

    @GetMapping("/change_password")
    public String showChangePasswordPage(@RequestParam("token") String token) {
        String result = userService.validatePasswordResetToken(token);
        if (result != null) {
            return "redirect:/login.html?lang=";
        } else {
            return "redirect:/updatePassword.html?lang=";
        }
    }

    @PostMapping("/save_password")
    public String savePassword(@Validated PasswordDto passwordDto) {
        String result = userService.validatePasswordResetToken(passwordDto.getToken());
        if (result != null) {
            return "Bad";
        }
        User user = userService.getUser(passwordDto.getToken());
        if (user != null) {
            userService.changeUserPassword(user, passwordDto.getNewPassword());
            return "Ok";
        } else {
            return "Invalid";
        }
    }

    @GetMapping("/resend_registration_token")
    public String resendRegistrationToken(HttpServletRequest request, @RequestParam("token") String existingToken) {
        Token newToken = userService.generateNewVerificationToken(existingToken);

        User user = userService.getUser(newToken.getConfirmationToken());
        String appUrl =
                "http://" + request.getServerName() +
                        ":" + request.getServerPort() +
                        request.getContextPath();
//        SimpleMailMessage email =
//                constructResendVerificationTokenEmail(appUrl, request.getLocale(), newToken, user);
//        mailSender.send(email);

        return "message.resendToken";
    }
}
