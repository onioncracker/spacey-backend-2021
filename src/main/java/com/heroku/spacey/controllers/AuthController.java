package com.heroku.spacey.controllers;

import com.heroku.spacey.dto.user.LoginDto;
import com.heroku.spacey.dto.user.UserRegisterDto;
import com.heroku.spacey.entity.LoginInfo;
import com.heroku.spacey.utils.security.JwtTokenProvider;
import com.heroku.spacey.services.IUserService;
import com.heroku.spacey.services.impl.MailServiceImpl;
import com.heroku.spacey.services.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AuthController {
    private static final String CONFIRM_REGISTRATION_TOPIC = "Confirm your account spacey.com";
    private final IUserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final MailServiceImpl mailServiceImpl;

    public AuthController(UserServiceImpl userService,
                          AuthenticationManager authenticationManager,
                          JwtTokenProvider jwtTokenProvider,
                          MailServiceImpl mailServiceImpl) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.mailServiceImpl = mailServiceImpl;
    }

    @PostMapping("/register")
    public ResponseEntity<String> userRegistration(@RequestBody @Validated UserRegisterDto registerDto) {
        String message = "http://localhost:8080/confirm_register?token=";
        userService.registerUser(registerDto);
        mailServiceImpl.sendSimpleMessageWithTemplate(registerDto.getEmail(), CONFIRM_REGISTRATION_TOPIC, message + "");
        return ResponseEntity.ok("user registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        Authentication authenticate = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        LoginInfo user = (LoginInfo) authenticate.getPrincipal();
        return ResponseEntity.ok()
            .header(HttpHeaders.AUTHORIZATION, jwtTokenProvider.generateToken(user))
            .body("successfully logged in");
    }

    @PostMapping("/recover_password")
    public ResponseEntity recoverPassword(@RequestBody String email) {
        log.info(email);
        return null;
    }
}
