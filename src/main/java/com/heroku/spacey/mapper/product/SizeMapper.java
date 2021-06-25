package com.heroku.spacey.mapper.product;

import com.heroku.spacey.dto.size.SizeProductDto;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class SizeMapper implements RowMapper<SizeProductDto> {
    @Override
    public SizeProductDto mapRow(ResultSet resultSet, int i) throws SQLException {
        SizeProductDto size = new SizeProductDto();
        size.setId(resultSet.getLong("sizeid"));
        size.setName(resultSet.getString("sizename"));
        return size;
    }
}

