package com.heroku.spacey.dao.common;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IdMapper implements RowMapper<Integer> {
    private final String nameId;

    public IdMapper() {
        this.nameId = "id";
    }

    public IdMapper(String nameId) {
        this.nameId = nameId;
    }

    @Override
    public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
        return resultSet.getInt(nameId);
    }
}
