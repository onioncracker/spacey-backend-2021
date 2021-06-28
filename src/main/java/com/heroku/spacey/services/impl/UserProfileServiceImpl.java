package com.heroku.spacey.services.impl;

import com.heroku.spacey.dao.UserProfileDao;
import com.heroku.spacey.dto.user.UserProfileDto;
import com.heroku.spacey.services.UserProfileService;
import com.heroku.spacey.utils.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileDao userProfileDao;
    private final SecurityUtils securityUtils;

    @Override
    public UserProfileDto getUserInfo() {
        return userProfileDao.getUserInfo(securityUtils.getUserIdByToken());
    }

    @Override
    @Transactional
    public void updateUserInfo(UserProfileDto userProfileDto) {
        userProfileDao.updateUserInfo(userProfileDto, securityUtils.getUserIdByToken());
    }
}
