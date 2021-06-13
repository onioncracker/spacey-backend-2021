package com.heroku.spacey.mapper.product;

import com.heroku.spacey.entity.Color;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ColorForProductMapper implements RowMapper<Color> {
    @Override
    public Color mapRow(ResultSet resultSet, int i) throws SQLException {
        Color color = new Color();
        color.setId(resultSet.getLong("color_id"));
        color.setName(resultSet.getString("color_name"));
        return color;
    }
}
