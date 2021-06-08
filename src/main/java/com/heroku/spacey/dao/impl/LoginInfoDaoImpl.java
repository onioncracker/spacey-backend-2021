package com.heroku.spacey.dao.impl;

import com.heroku.spacey.dao.LoginInfoDao;
import com.heroku.spacey.entity.LoginInfo;
import com.heroku.spacey.mapper.LoginInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Objects;

@Slf4j
@Repository
@PropertySource("classpath:sql/login_info_queries.properties")
public class LoginInfoDaoImpl implements LoginInfoDao {
    private final JdbcTemplate jdbcTemplate;
    private LoginInfoMapper mapper;

    @Value("${get_login_info_by_email}")
    private String getLoginInfoByEmail;

    @Value("${insert_login_info}")
    private String insertLoginInfo;

    public LoginInfoDaoImpl(@Autowired JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = new LoginInfoMapper();
    }


    @Override
    public LoginInfo getLoginInfoByEmail(String email) {
        var result = jdbcTemplate.query(getLoginInfoByEmail, mapper, email);
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

    @Override
    public int insert(LoginInfo loginInfo) {
        int returnId;
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertLoginInfo, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, loginInfo.getEmail());
            ps.setString(2, loginInfo.getPassword());
            ps.setString(3, loginInfo.getUserRole());
            ps.setString(4, loginInfo.getFirstName());
            ps.setString(5, loginInfo.getLastName());
            ps.setString(6, loginInfo.getPhoneNumber());
            return ps;
        }, holder);

        if (Objects.requireNonNull(holder.getKeys()).size() > 1) {
            returnId = (int) holder.getKeys().get("loginId");
        } else {
            returnId = Objects.requireNonNull(holder.getKey()).intValue();
        }
        return returnId;
    }
}
