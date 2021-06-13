package com.heroku.spacey.dao;

import com.heroku.spacey.entity.User;

public interface UserDao {
    User getUserByEmail(String email);
    long insert(User user);
}
