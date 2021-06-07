package com.heroku.spacey.dao;

import com.heroku.spacey.entity.LoginInfo;

public interface LoginInfoDao {
    LoginInfo getLoginInfoByEmail(String email);
    boolean exists(String id);
    int insert(LoginInfo loginInfo);
}
