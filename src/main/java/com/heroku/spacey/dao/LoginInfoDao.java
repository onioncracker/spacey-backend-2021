package com.heroku.spacey.dao;

import com.heroku.spacey.entity.LoginInfo;

public interface LoginInfoDao {
    LoginInfo getLoginInfoByEmail(String email);
    int insert(LoginInfo loginInfo);
}
