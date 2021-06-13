package com.heroku.spacey.controllers;

import com.heroku.spacey.dto.auth.UserTokenDto;
import com.heroku.spacey.dto.user.LoginDto;
import com.heroku.spacey.dto.user.UserRegisterDto;
import com.heroku.spacey.entity.LoginInfo;
import com.heroku.spacey.utils.security.JwtTokenProvider;
import com.heroku.spacey.services.UserService;
import com.heroku.spacey.services.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(UserServiceImpl userService,
                          AuthenticationManager authenticationManager,
                          JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/register")
    public ResponseEntity userRegistration(@RequestBody @Validated UserRegisterDto registerDto) {
        if (userService.userExists(registerDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("user with such email already exists");
        }
        userService.registerUser(registerDto);
        Authentication authenticate = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(registerDto.getEmail(), registerDto.getPassword()));
        LoginInfo user = (LoginInfo) authenticate.getPrincipal();
        String token = jwtTokenProvider.generateToken(user);
        return ResponseEntity.ok()
            .header(HttpHeaders.AUTHORIZATION, token)
            .body(new UserTokenDto(token));
    }

    @PostMapping("/login")
    public ResponseEntity<UserTokenDto> login(@RequestBody LoginDto loginDto) {
        Authentication authenticate = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        LoginInfo user = (LoginInfo) authenticate.getPrincipal();
        String token = jwtTokenProvider.generateToken(user);
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
