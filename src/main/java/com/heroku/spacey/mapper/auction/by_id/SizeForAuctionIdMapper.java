package com.heroku.spacey.mapper.auction.by_id;

import com.heroku.spacey.entity.Size;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SizeForAuctionIdMapper implements RowMapper<Size> {
    @Override
    public Size mapRow(ResultSet resultSet, int i) throws SQLException {
        Size size = new Size();
        size.setId(resultSet.getLong("s_id"));
        size.setName(resultSet.getString("sizename"));
        size.setQuantity(resultSet.getLong("size_quantity"));
        return size;
    }
}
