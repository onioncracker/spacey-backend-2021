package com.heroku.spacey.mapper;

import com.heroku.spacey.entity.Color;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ColorMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        Color color = new Color();
        color.setId(resultSet.getLong("colorid"));
        color.setName(resultSet.getString("color"));
        return color;
    }
}
