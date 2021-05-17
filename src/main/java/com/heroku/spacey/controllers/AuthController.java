package com.heroku.spacey.controllers;

import com.heroku.spacey.dto.LoginDto;
import com.heroku.spacey.dto.UserRegisterDto;
import com.heroku.spacey.models.UserInfoModel;
import com.heroku.spacey.security.JwtTokenProvider;
import com.heroku.spacey.services.UserInfoService;
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
    private UserInfoService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/register")
    public ResponseEntity userRegistration(@RequestBody UserRegisterDto registerDto){
        try{
            userService.create(registerDto);
            return ResponseEntity.ok("user registered successfully");
        }catch (Exception e){
            return ResponseEntity.unprocessableEntity().build(); // TODO change status code
        }
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDto loginDto){
        try{
            Authentication authenticate = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
            UserInfoModel user = (UserInfoModel) authenticate.getPrincipal();
            return ResponseEntity.ok()
                    .header(
                            HttpHeaders.AUTHORIZATION,
                            jwtTokenProvider.generateToken(user)
                    )
                    .body("successfully logged in");
        }catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
