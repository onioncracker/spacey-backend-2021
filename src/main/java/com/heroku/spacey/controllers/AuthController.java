package com.heroku.spacey.controllers;

import com.heroku.spacey.dto.auth.UserTokenDto;
import com.heroku.spacey.dto.user.LoginDto;
import com.heroku.spacey.dto.user.UserRegisterDto;
import com.heroku.spacey.services.IUserService;
import com.heroku.spacey.services.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class AuthController {
    private final IUserService userService;

    public AuthController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity userRegistration(@RequestBody @Validated UserRegisterDto registerDto) {
        if (userService.userExists(registerDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("user with such email already exists");
        }
        userService.registerUser(registerDto);
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

    @PostMapping("/recover_password")
    public ResponseEntity recoverPassword(@RequestBody String email) {
        log.info(email);
        return null;
    }
}
