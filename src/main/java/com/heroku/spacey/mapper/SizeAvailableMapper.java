package com.heroku.spacey.mapper;

import com.heroku.spacey.dto.product.SizeDto;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class SizeAvailableMapper implements RowMapper<SizeDto> {
    @Override
    public SizeDto mapRow(ResultSet resultSet, int i) throws SQLException {
        SizeDto sizeDto = new SizeDto();
        sizeDto.setId(resultSet.getLong("sizeid"));
        sizeDto.setName(resultSet.getString("sizename"));
        sizeDto.setAvailable(resultSet.getInt("quantity") > 0);
        return sizeDto;
    }
}
