package com.heroku.spacey.mapper.auction;

import com.heroku.spacey.entity.Auction;
import com.heroku.spacey.entity.Product;
import com.heroku.spacey.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

//TODO: finish auction mapper for product fields
public class AuctionMapper implements RowMapper<Auction> {
    @Override
    public Auction mapRow(ResultSet resultSet, int i) throws SQLException {
        Auction auction = new Auction();
        auction.setAuctionId(resultSet.getLong("auctionid"));
        auction.setUserId(resultSet.getLong("userid"));
        auction.setAuctionName(resultSet.getString("auctionname"));
        auction.setAuctionType(resultSet.getBoolean("auctiontype"));
        auction.setStartPrice(resultSet.getDouble("startprice"));
        auction.setEndPrice(resultSet.getDouble("endprice"));
        auction.setStatus(resultSet.getString("status"));

        UserForAuctionMapper userMapper = new UserForAuctionMapper();
        User user = userMapper.mapRow(resultSet, i);
        return auction;
    }
}
