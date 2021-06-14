package com.heroku.spacey.services.impl;

import com.heroku.spacey.dao.RoleDao;
import com.heroku.spacey.dao.StatusDao;
import com.heroku.spacey.dao.UserDao;
import com.heroku.spacey.dto.user.UserRegisterDto;
import com.heroku.spacey.services.UserService;
import com.heroku.spacey.entity.User;
import com.heroku.spacey.error.UserNotActivatedException;
import com.heroku.spacey.utils.Roles;
import com.heroku.spacey.utils.Statuses;
import com.heroku.spacey.utils.convertors.UserConvertor;
import com.heroku.spacey.utils.security.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final BCryptPasswordEncoder passwordEncoder;
    private UserDao userDao;
    private RoleDao roleDao;
    private StatusDao statusDao;
    private final UserConvertor userConvertor;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;


    public UserServiceImpl(@Autowired BCryptPasswordEncoder passwordEncoder,
                           @Autowired UserDao userDao,
                           @Autowired RoleDao roleDao,
                           @Autowired StatusDao statusDao,
                           @Autowired UserConvertor userConvertor,
                           @Autowired AuthenticationManager authenticationManager,
                           @Autowired JwtTokenProvider jwtTokenProvider) {
        this.passwordEncoder = passwordEncoder;
        this.userDao = userDao;
        this.statusDao = statusDao;
        this.userConvertor = userConvertor;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.roleDao = roleDao;
    }

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDao.getUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("404 user not found");
        }
        user.setUserRole(roleDao.getRoleName(user.getRoleId()));
        return user;
    }

    @Override
    public User registerUser(UserRegisterDto registerDto) {
        String encodedPassword = passwordEncoder.encode(registerDto.getPassword());
        User user = this.userConvertor.adapt(registerDto);
        user.setPassword(encodedPassword);
        user.setUserRole(Roles.USER.name());

        Long roleId = roleDao.getRoleId(Roles.USER.name());
        if (roleId == null) {
            roleId = roleDao.insertRole(Roles.USER.name());
            log.info("role user created with id: " + roleId);

        }
        user.setRoleId(roleId);

        Long statusId = statusDao.getStatusId(Statuses.UNACTIVATED.name());
        if (statusId == null) {
            statusId = statusDao.insertStatus(Statuses.UNACTIVATED.name());
            log.info("status 'unactivated' created with id: " + statusId);

        }
        user.setStatusId(statusId);

        userDao.insert(user);
        return user;
    }

    @Override
    public boolean userExists(String email) {
        User user = userDao.getUserByEmail(email);
        return user != null;
    }

    @Override
    public String generateAuthenticationToken(String email, String password) {
        Authentication authenticate = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(email, password));
        User user = (User) authenticate.getPrincipal();
//        if (statusDao.getStatusName(user.getStatusId()).equals(Statuses.UNACTIVATED.name())) {
//            throw new UserNotActivatedException("v kmv ");
//        }
        return jwtTokenProvider.generateToken(user);
    }
}
