package com.heroku.spacey.controllers;

import com.heroku.spacey.dto.LoginDto;
import com.heroku.spacey.dto.UserRegisterDto;
import com.heroku.spacey.models.UserModel;
import com.heroku.spacey.security.JwtTokenProvider;
import com.heroku.spacey.services.UserService;
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

@RestController
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/register")
    public ResponseEntity userRegistration(@RequestBody UserRegisterDto registerDto) {
        try {
            userService.create(registerDto);
            return ResponseEntity.ok("user registered successfully");  // TODO change to returning auth token
        }catch (Exception e){
            return ResponseEntity.unprocessableEntity().body(e.toString()); // TODO change status code
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
        }catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.toString());
        }
    }
}
