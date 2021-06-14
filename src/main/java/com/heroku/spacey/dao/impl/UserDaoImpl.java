package com.heroku.spacey.dao.impl;

import com.heroku.spacey.dao.UserDao;
import com.heroku.spacey.entity.Category;
import com.heroku.spacey.entity.User;
import com.heroku.spacey.mapper.UserMapper;
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
import java.util.List;
import java.util.Objects;

@Slf4j
@Repository
@PropertySource("classpath:sql/user_queries.properties")
public class UserDaoImpl implements UserDao {
    private final JdbcTemplate jdbcTemplate;
    private UserMapper mapper;

    @Value("${get_user_by_email}")
    private String getUserByEmail;

    @Value("${get_user_by_token_id}")
    private String getUserByTokenId;

    @Value("${insert_user}")
    private String insertUser;

    @Value("${update_user}")
    private String updateUser;

    public UserDaoImpl(@Autowired JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = new UserMapper();
    }


    @Override
    public User getUserByEmail(String email) {
        var result = jdbcTemplate.query(getUserByEmail, mapper, email);
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

    @Override
    public User getUserByTokenId(Long id) {
        List<User> users = Objects.requireNonNull(jdbcTemplate).query(getUserByTokenId, mapper, id);
        if (users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }

    @Override
    public long insert(User user) {
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertUser, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, user.getRoleId());
            ps.setLong(2, user.getStatusId());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getFirstName());
            ps.setString(6, user.getLastName());
            return ps;
        }, holder);
        return (long) Objects.requireNonNull(holder.getKeys()).get("userId");
    }

    @Override
    public void updateUser(User user) {
        Object[] params = new Object[]{user.getTokenId(), user.getPassword(),
                user.getFirstName(), user.getLastName(), user.getUserId()};
        Objects.requireNonNull(jdbcTemplate).update(updateUser, params);
    }
}
