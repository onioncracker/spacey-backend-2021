package com.heroku.spacey.dao;

import com.heroku.spacey.dto.user.UserProfileDto;

public interface UserProfileDao {

    UserProfileDto getUserInfo(Long userId);

    void updateUserInfo(UserProfileDto userProfileDto, Long userId);
}
