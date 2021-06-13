package com.heroku.spacey.mapper;

import com.heroku.spacey.entity.Status;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StatusMapper implements RowMapper<Status> {
    @Override
    public Status mapRow(ResultSet resultSet, int i) throws SQLException {
        Status role = new Status();
        role.setId(resultSet.getLong("statusid"));
        role.setStatusName(resultSet.getString("statusname"));
        return role;
    }
}