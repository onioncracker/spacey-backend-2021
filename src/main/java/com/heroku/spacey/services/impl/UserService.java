package com.heroku.spacey.services.impl;

import com.heroku.spacey.dto.user.UserRegisterDto;
import com.heroku.spacey.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // this is for testing untill db is configured
        UserModel userModel = new UserModel("user", passwordEncoder.encode("password"));
        userModel.setGrantedAuthorities("ROLE_USER");

        return userModel;        // TODO here implement returning user object from database
    }

    public UserDetails create(UserRegisterDto registerDto){
        String encodedPassword = passwordEncoder.encode(registerDto.getPassword());
        UserModel userDetails = new UserModel(registerDto.getEmail(), encodedPassword);
        userDetails.setGrantedAuthorities("ROLE_USER");  // TODO change role setting

        // TODO here also implement creation of user in database
        return userDetails;
    }
}
