package com.heroku.spacey.services.impl;

import com.heroku.spacey.dao.RoleDao;
import com.heroku.spacey.dao.StatusDao;
import com.heroku.spacey.dao.UserDao;
import com.heroku.spacey.dao.TokenDao;
import com.heroku.spacey.dto.user.UserRegisterDto;
import com.heroku.spacey.entity.Token;
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

import java.util.Calendar;
import java.util.UUID;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final BCryptPasswordEncoder passwordEncoder;
    private UserDao userDao;
    private RoleDao roleDao;
    private StatusDao statusDao;
    private final TokenDao tokenDao;
    private final UserConvertor userConvertor;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public UserServiceImpl(@Autowired BCryptPasswordEncoder passwordEncoder,
                           @Autowired UserDao userDao,
                           @Autowired RoleDao roleDao,
                           @Autowired StatusDao statusDao,
                           @Autowired TokenDao tokenDao,
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
        user.setActivation(false);

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
    public User userExists(String email) {
        return userDao.getUserByEmail(email);
    }

    @Override
    public String generateAuthenticationToken(String email, String password) {
        Authentication authenticate = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(email, password));
        User user = (User) authenticate.getPrincipal();
//        if (statusDao.getStatusName(user.getStatusId()).equals(Status.UNACTIVATED.name())) {
//            throw new UserNotActivatedException("v kmv ");
//        }
        return jwtTokenProvider.generateToken(user);
    }

    @Override
    public User getUser(String verificationToken) {
        Long tokenId = tokenDao.findByToken(verificationToken).getTokenId();
        return userDao.getUserByTokenId(tokenId);
    }

    @Override
    public void createVerificationToken(User user, String token) {
        Token verificationToken = new Token();
        verificationToken.setConfirmationToken(token);
        Long tokenId = tokenDao.insert(verificationToken);
        user.setTokenId(tokenId);
        userDao.updateUser(user);
    }

    @Override
    public void updateUserStatus(User user) {
        user.setStatusId(Status.ACTIVATED.value);
        userDao.updateUser(user);
    }

    @Override
    public Token getVerificationToken(String token) {
        return tokenDao.findByToken(token);
    }

    @Override
    public Token generateNewVerificationToken(String existingVerificationToken) {
        Token token = tokenDao.findByToken(existingVerificationToken);
        token.updateToken(UUID.randomUUID().toString());
        return tokenDao.findByTokenId(tokenDao.insert(token));
    }

    @Override
    public void changeUserPassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        userDao.updateUser(user);
    }

    @Override
    public String validatePasswordResetToken(String token) {
        final Token passToken = tokenDao.findByToken(token);
        return !isTokenFound(passToken) ? "invalidToken" : isTokenExpired(passToken) ? "expired" : null;
    }

    public void deleteTokenById(Long id) {
        tokenDao.delete(id);
    }

    private boolean isTokenFound(Token passToken) {
        return passToken != null;
    }

    private boolean isTokenExpired(Token passToken) {
        final Calendar cal = Calendar.getInstance();
        return passToken.getDate().before(cal.getTime());
    }
}
