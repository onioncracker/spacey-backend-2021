package com.heroku.spacey.mapper.auction;

import com.heroku.spacey.entity.Auction;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuctionMapper implements RowMapper<Auction> {
    @Override
    public Auction mapRow(ResultSet resultSet, int i) throws SQLException {
        return null;
    }
}
