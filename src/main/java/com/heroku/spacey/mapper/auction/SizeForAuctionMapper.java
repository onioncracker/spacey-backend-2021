package com.heroku.spacey.mapper.auction;

import com.heroku.spacey.entity.Size;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

//TODO: finish size for auction mapper
public class SizeForAuctionMapper implements RowMapper<Size> {
    @Override
    public Size mapRow(ResultSet resultSet, int i) throws SQLException {
        Size size = new Size();
        size.setId(resultSet.getLong("size_id"));
        size.setName(resultSet.getString("size_name"));
        size.setQuantity(resultSet.getLong("size_quantity"));
        return size;
    }
}
