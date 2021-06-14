package com.heroku.spacey.services.impl;

import com.heroku.spacey.dao.RoleDao;
import com.heroku.spacey.dao.StatusDao;
import com.heroku.spacey.dao.UserDao;
import com.heroku.spacey.dao.VerificationTokenDao;
import com.heroku.spacey.dto.user.UserRegisterDto;
import com.heroku.spacey.entity.VerificationToken;
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
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final BCryptPasswordEncoder passwordEncoder;
    private UserDao userDao;
    private RoleDao roleDao;
    private StatusDao statusDao;
    private final VerificationTokenDao tokenDao;
    private final UserConvertor userConvertor;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;


    public UserServiceImpl(@Autowired BCryptPasswordEncoder passwordEncoder,
                           @Autowired UserDao userDao,
                           @Autowired RoleDao roleDao,
                           @Autowired StatusDao statusDao,
                           @Autowired VerificationTokenDao tokenDao,
                           @Autowired UserConvertor userConvertor,
                           @Autowired AuthenticationManager authenticationManager,
                           @Autowired JwtTokenProvider jwtTokenProvider) {
        this.passwordEncoder = passwordEncoder;
        this.userDao = userDao;
        this.statusDao = statusDao;
        this.tokenDao = tokenDao;
        this.userConvertor = userConvertor;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.roleDao = roleDao;
    }

    @Override
    @Transactional
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDao.getUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("404 user not found");
        }
        user.setUserRole(roleDao.getRoleName(user.getRoleId()));
        return user;
    }

    @Override
    @Transactional
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
        user.setEnabled(false);

        userDao.insert(user);

        return user;
    }

    @Override
    @Transactional
    public boolean userExists(String email) {
        User user = userDao.getUserByEmail(email);
        return user != null;
    }

    @Override
    @Transactional
    public String generateAuthenticationToken(String email, String password) {
        Authentication authenticate = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(email, password));
        User user = (User) authenticate.getPrincipal();
//        if (statusDao.getStatusName(user.getStatusId()).equals(Statuses.UNACTIVATED.name())) {
//            throw new UserNotActivatedException("v kmv ");
//        }
        return jwtTokenProvider.generateToken(user);
    }

    @Override
    @Transactional
    public User getUser(String verificationToken) {
        Long tokenId = tokenDao.findByToken(verificationToken).getTokenId();
        return userDao.getUserByTokenId(tokenId);
    }

    @Override
    @Transactional
    public void createVerificationToken(User user, String token) {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setConfirmationToken(token);
        Long tokenId = tokenDao.insert(verificationToken);
        user.setTokenId(tokenId);
        userDao.updateUser(user);
    }

    @Override
    @Transactional
    public VerificationToken getVerificationToken(String token) {
        return tokenDao.findByToken(token);
    }
}
