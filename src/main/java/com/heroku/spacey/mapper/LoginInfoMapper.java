package com.heroku.spacey.mapper;

import com.heroku.spacey.entity.LoginInfo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginInfoMapper implements RowMapper<LoginInfo> {
    @Override
    public LoginInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setLoginId(rs.getInt("loginid"));
        loginInfo.setEmail(rs.getString("email"));
        loginInfo.setPassword(rs.getString("pass"));
        loginInfo.setFirstName(rs.getString("firstname"));
        loginInfo.setLastName(rs.getString("lastname"));
        loginInfo.setUserRole(rs.getString("userrole"));
        loginInfo.setStatus(rs.getString("status"));
        loginInfo.setPhoneNumber(rs.getString("phonenumber"));
        return loginInfo;
    }
}
