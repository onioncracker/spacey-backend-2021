package com.heroku.spacey.mapper;

import com.heroku.spacey.entity.Size;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SizeMapper implements RowMapper<Size> {
    @Override
    public Size mapRow(ResultSet resultSet, int i) throws SQLException {
        Size size = new Size();
        size.setId(resultSet.getLong("sizeid"));
        size.setName(resultSet.getString("sizename"));
        size.setQuantity(resultSet.getLong("quantity"));
        return size;
    }
}
