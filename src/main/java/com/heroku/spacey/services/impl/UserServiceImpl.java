package com.heroku.spacey.services.impl;

import com.heroku.spacey.dao.RoleDao;
import com.heroku.spacey.dao.StatusDao;
import com.heroku.spacey.dao.UserDao;
import com.heroku.spacey.dao.TokenDao;
import com.heroku.spacey.dto.user.UserRegisterDto;
import com.heroku.spacey.entity.Token;
import com.heroku.spacey.error.UserNotActivatedException;
import com.heroku.spacey.services.TokenService;
import com.heroku.spacey.services.UserService;
import com.heroku.spacey.entity.User;
import com.heroku.spacey.utils.Roles;
import com.heroku.spacey.utils.Status;
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
import org.webjars.NotFoundException;

import java.util.Calendar;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final BCryptPasswordEncoder passwordEncoder;
    private UserDao userDao;
    private RoleDao roleDao;
    private StatusDao statusDao;
    private final TokenDao tokenDao;
    private final TokenService tokenService;
    private final UserConvertor userConvertor;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public UserServiceImpl(@Autowired BCryptPasswordEncoder passwordEncoder,
                           @Autowired UserDao userDao,
                           @Autowired RoleDao roleDao,
                           @Autowired StatusDao statusDao,
                           @Autowired TokenDao tokenDao,
                           @Autowired TokenService tokenService,
                           @Autowired UserConvertor userConvertor,
                           @Autowired AuthenticationManager authenticationManager,
                           @Autowired JwtTokenProvider jwtTokenProvider) {
        this.passwordEncoder = passwordEncoder;
        this.userDao = userDao;
        this.statusDao = statusDao;
        this.tokenDao = tokenDao;
        this.tokenService = tokenService;
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

        Long statusId = statusDao.getStatusId(Status.UNACTIVATED.name());
        if (statusId == null) {
            statusId = statusDao.insertStatus(Status.UNACTIVATED.name());
            log.info("status 'unactivated' created with id: " + statusId);
        }
        user.setStatusId(statusId);

        Long userId = userDao.insert(user);
        user.setUserId(userId);

        return user;
    }

    @Override
    public boolean userExists(String email) {
        return userDao.getUserByEmail(email) != null;
    }

    @Override
    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Override
    public String generateAuthenticationToken(String email, String password) {
        Authentication authenticate = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(email, password));
        User user = (User) authenticate.getPrincipal();
        if (statusDao.getStatusName(user.getStatusId()).equals(Status.UNACTIVATED.name())) {
            throw new UserNotActivatedException("v kmv ");
        }
        return jwtTokenProvider.generateToken(user);
    }

    @Override
    public User getUserByToken(String verificationToken) {
        Long tokenId = tokenDao.findByToken(verificationToken).getTokenId();
        return userDao.getUserByTokenId(tokenId);
    }

    private void updateUserStatus(User user) {
        user.setStatusId(Status.ACTIVATED.value);
        userDao.updateUserStatus(user);
    }

    @Override
    public void confirmUserRegistration(String token) {
        Token verificationToken = tokenService.getVerificationToken(token);
        if (verificationToken == null) {
            throw new NotFoundException("Verification token not found");
        }

        User user = getUserByToken(token);
        user.setStatusId(Status.ACTIVATED.value);
        userDao.updateUserStatus(user);
    }
}
