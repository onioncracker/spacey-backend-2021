package com.heroku.spacey.controllers;

import com.heroku.spacey.dto.user.UserProfileDto;
import com.heroku.spacey.services.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/profile")
public class UserProfileController {

    private final UserProfileService userProfileService;

    @Secured("ROLE_USER")
    @GetMapping("")
    public UserProfileDto getUserInfoById() {
        return userProfileService.getUserInfo();
    }

    @Secured("ROLE_USER")
    @PutMapping("/edit")
    public HttpStatus updateUserProfile(@RequestBody UserProfileDto userProfileDto) {
        userProfileService.updateUserInfo(userProfileDto);
        return HttpStatus.OK;
    }
}
