package com.heroku.spacey.mapper;

import com.heroku.spacey.entity.Role;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleMapper implements RowMapper<Role> {
    @Override
    public Role mapRow(ResultSet resultSet, int i) throws SQLException {
        Role role = new Role();
        role.setId(resultSet.getLong("roleid"));
        role.setRoleName(resultSet.getString("rolename"));
        return role;
    }
}