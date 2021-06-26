package com.heroku.spacey.mapper.auction;

import com.heroku.spacey.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

//TODO: finish user for auction mapper
public class UserForAuctionMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setUserId(resultSet.getLong("userId"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("pass"));
        user.setFirstName(resultSet.getString("firstName"));
        user.setLastName(resultSet.getString("lastName"));
        user.setRoleId(resultSet.getLong("roleId"));
        user.setStatusId(resultSet.getLong("statusId"));
        return user;
    }
}
