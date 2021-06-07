package com.heroku.spacey.dao;

import com.heroku.spacey.entity.LoginInfo;

public interface LoginInfoDao {
    LoginInfo getLoginInfoByEmail(String email);
    long insert(LoginInfo loginInfo);
}
