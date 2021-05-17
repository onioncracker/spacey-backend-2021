package com.heroku.spacey.services;

import com.heroku.spacey.dto.UserRegisterDto;
import com.heroku.spacey.models.UserInfoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // this is for testing untill db is configured
        UserInfoModel userInfoModel = new UserInfoModel("user", "password");
        userInfoModel.setGrantedAuthorities("ROLE_USER");

        return userInfoModel;        // TODO here implement returning user object from database
    }

    public UserDetails create(UserRegisterDto registerDto){
        String encodedPassword = passwordEncoder.encode(registerDto.getPassword());
        UserInfoModel userDetails = new UserInfoModel(registerDto.getEmail(), encodedPassword);
        userDetails.setGrantedAuthorities("ROLE_USER");  // TODO change role setting

        // TODO here also implement creation of user in database
        return userDetails;
    }
}
