package com.heroku.spacey.mapper;

import com.heroku.spacey.dto.product.SizeDto;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
@Component
public class SizeMapper implements RowMapper<SizeDto> {

    @Override
    public SizeDto mapRow(ResultSet resultSet, int i) throws SQLException {
        SizeDto sizeDto = new SizeDto();
        sizeDto.setSize(resultSet.getString("sizename"));
        sizeDto.setQuantity(resultSet.getInt("quantity"));
        return sizeDto;
    }
}
