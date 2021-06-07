package com.heroku.spacey.services.impl;

import com.heroku.spacey.dao.LoginInfoDao;
import com.heroku.spacey.dto.user.UserRegisterDto;
import com.heroku.spacey.entity.LoginInfo;
import com.heroku.spacey.services.IUserService;
import com.heroku.spacey.utils.Role;
import com.heroku.spacey.utils.convertors.LoginInfoConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    private final BCryptPasswordEncoder passwordEncoder;
    private LoginInfoDao loginInfoDao;
    private final LoginInfoConvertor loginInfoConvertor;

    public UserServiceImpl(@Autowired BCryptPasswordEncoder passwordEncoder,
                           @Autowired LoginInfoDao loginInfoDao,
                           @Autowired LoginInfoConvertor loginInfoConvertor) {
        this.passwordEncoder = passwordEncoder;
        this.loginInfoDao = loginInfoDao;
        this.loginInfoConvertor = loginInfoConvertor;
    }

    @Override
    public LoginInfo loadUserByUsername(String email) throws UsernameNotFoundException {
        LoginInfo loginInfo = loginInfoDao.getLoginInfoByEmail(email);
        if (loginInfo == null) {
            throw new UsernameNotFoundException("404 user not found");
        }
        return loginInfo;

    }


    public LoginInfo registerUser(UserRegisterDto registerDto) {
        String encodedPassword = passwordEncoder.encode(registerDto.getPassword());
        LoginInfo loginInfo = loginInfoConvertor.adapt(registerDto);
        loginInfo.setPassword(encodedPassword);
        loginInfo.setUserRole(Role.USER.name());
        loginInfoDao.insert(loginInfo);
        return loginInfo;
    }

    public boolean userExists(String email){
        LoginInfo loginInfo = loginInfoDao.getLoginInfoByEmail(email);
        if (loginInfo == null) {
            return false;
        }
        return true;
    }
}
