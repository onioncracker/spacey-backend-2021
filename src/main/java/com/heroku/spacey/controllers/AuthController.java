package com.heroku.spacey.controllers;

import com.heroku.spacey.dto.auth.LoginDto;
import com.heroku.spacey.dto.user.UserRegisterDto;
import com.heroku.spacey.dto.auth.UserTokenDto;
import com.heroku.spacey.models.UserModel;
import com.heroku.spacey.security.JwtTokenProvider;
import com.heroku.spacey.services.MailServiceImpl;
import com.heroku.spacey.services.TokenService;
import com.heroku.spacey.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
public class AuthController {
    private static final String CONFIRM_REGISTRATION_TOPIC = "Confirm your account spacey.com";
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final MailServiceImpl mailServiceImpl;
    private final TokenService tokenService;

    public AuthController(UserService userService,
                          AuthenticationManager authenticationManager,
                          JwtTokenProvider jwtTokenProvider,
                          MailServiceImpl mailServiceImpl,
                          TokenService tokenService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.mailServiceImpl = mailServiceImpl;
        this.tokenService = tokenService;
    }

    @PostMapping("/register")
    public ResponseEntity userRegistration(@RequestBody UserRegisterDto registerDto) {
        String message = "http://localhost:8080/confirm_register?token=";
        try {
            userService.create(registerDto);
            UserTokenDto userTokenDto = new UserTokenDto(UUID.randomUUID().toString(), registerDto.getEmail());
            tokenService.addUserToken(userTokenDto);
            mailServiceImpl.sendSimpleMessageWithTemplate(registerDto.getEmail(), CONFIRM_REGISTRATION_TOPIC, message + userTokenDto.getToken());
            return ResponseEntity.ok("user registered successfully");
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(e.toString());
        }
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDto loginDto) {
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
            UserModel user = (UserModel) authenticate.getPrincipal();
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, jwtTokenProvider.generateToken(user))
                    .body("successfully logged in");
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.toString());
        }
    }

    @PostMapping("/recover_password")
    public ResponseEntity recoverPassword(@RequestBody String email) {
        log.info(email);
        return null;
    }
}
