package com.heroku.spacey.dao;

import com.heroku.spacey.entity.User;

public interface UserDao {
    User getUserByEmail(String email);

    User getUserByTokenId(Long id);

    long insert(User user);

    void updateUser(User user);

    void updateUserStatus(User user);

    void updateUserActivation(User user);
}
