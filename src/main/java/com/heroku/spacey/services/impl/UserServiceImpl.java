package com.heroku.spacey.services.impl;

import com.heroku.spacey.dao.LoginInfoDao;
import com.heroku.spacey.dto.user.UserRegisterDto;
import com.heroku.spacey.entity.LoginInfo;
import com.heroku.spacey.services.IUserService;
import com.heroku.spacey.utils.Role;
import com.heroku.spacey.utils.convertors.LoginInfoConvertor;
import com.heroku.spacey.utils.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    private final BCryptPasswordEncoder passwordEncoder;
    private LoginInfoDao loginInfoDao;
    private final LoginInfoConvertor loginInfoConvertor;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;


    public UserServiceImpl(@Autowired BCryptPasswordEncoder passwordEncoder,
                           @Autowired LoginInfoDao loginInfoDao,
                           @Autowired LoginInfoConvertor loginInfoConvertor,
                           @Autowired AuthenticationManager authenticationManager,
                           @Autowired JwtTokenProvider jwtTokenProvider) {
        this.passwordEncoder = passwordEncoder;
        this.loginInfoDao = loginInfoDao;
        this.loginInfoConvertor = loginInfoConvertor;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public LoginInfo loadUserByUsername(String email) throws UsernameNotFoundException {
        LoginInfo loginInfo = loginInfoDao.getLoginInfoByEmail(email);
        if (loginInfo == null) {
            throw new UsernameNotFoundException("404 user not found");
        }
        return loginInfo;

    }

    @Override
    public LoginInfo registerUser(UserRegisterDto registerDto) {
        String encodedPassword = passwordEncoder.encode(registerDto.getPassword());
        LoginInfo loginInfo = loginInfoConvertor.adapt(registerDto);
        loginInfo.setPassword(encodedPassword);
        loginInfo.setUserRole(Role.USER.name());
        loginInfoDao.insert(loginInfo);
        return loginInfo;
    }

    @Override
    public boolean userExists(String email) {
        LoginInfo loginInfo = loginInfoDao.getLoginInfoByEmail(email);
        return loginInfo != null;
    }

    @Override
    public String generateAuthenticationToken(String email, String password) {
        Authentication authenticate = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(email, password));
        LoginInfo user = (LoginInfo) authenticate.getPrincipal();
        return jwtTokenProvider.generateToken(user);
    }
}
