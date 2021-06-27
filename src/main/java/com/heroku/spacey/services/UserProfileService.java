package com.heroku.spacey.services;

import com.heroku.spacey.dto.user.UserProfileDto;

public interface UserProfileService {

    UserProfileDto getUserInfo();

    void updateUserInfo(UserProfileDto userProfileDto);
}
