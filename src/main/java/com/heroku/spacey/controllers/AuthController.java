package com.heroku.spacey.controllers;

import com.heroku.spacey.dto.auth.UserTokenDto;
import com.heroku.spacey.dto.user.LoginDto;
import com.heroku.spacey.dto.user.UserRegisterDto;
import com.heroku.spacey.services.UserService;
import com.heroku.spacey.services.impl.UserServiceImpl;
import com.heroku.spacey.utils.registration.OnRegistrationCompleteEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class AuthController {
    private final UserService userService;
    private final ApplicationEventPublisher eventPublisher;

    public AuthController(UserServiceImpl userService, ApplicationEventPublisher eventPublisher) {
        this.userService = userService;
        this.eventPublisher = eventPublisher;
    }

    @PostMapping("/register")
    public ResponseEntity userRegistration(@RequestBody @Validated UserRegisterDto registerDto, HttpServletRequest request) {
        if (userService.userExists(registerDto.getEmail())) {
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

//    @GetMapping("/registration_confirm")
//    public String confirmRegistration(WebRequest request, @RequestParam("token") String token) {
//        Locale locale = request.getLocale();
//
//        if (token == null) {
//
//        }
//        return "test";
//    }

    @PostMapping("/login")
    public ResponseEntity<UserTokenDto> login(@RequestBody LoginDto loginDto) {
        String token = userService.generateAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());
        return ResponseEntity.ok()
            .header(HttpHeaders.AUTHORIZATION, token)
            .body(new UserTokenDto(token));
    }

    @PostMapping("/recover_password")
    public ResponseEntity recoverPassword(@RequestBody String email) {
        log.info(email);
        return null;
    }
}
