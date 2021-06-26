package com.heroku.spacey.mapper.auction;

import com.heroku.spacey.entity.Auction;
import com.heroku.spacey.entity.Product;
import com.heroku.spacey.entity.Size;
import com.heroku.spacey.entity.User;
import com.heroku.spacey.mapper.UserMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuctionMapper implements RowMapper<Auction> {
    @Override
    public Auction mapRow(ResultSet resultSet, int i) throws SQLException {
        Auction auction = new Auction();
        auction.setAuctionId(resultSet.getLong("auctionid"));
        auction.setUserId(resultSet.getLong("userid"));
        auction.setProductId(resultSet.getLong("productid"));
        auction.setSizeId(resultSet.getLong("sizeid"));
        auction.setAmount(resultSet.getInt("amount"));
        auction.setAuctionName(resultSet.getString("auctionname"));
        auction.setAuctionType(resultSet.getBoolean("auctiontype"));
        auction.setStartPrice(resultSet.getDouble("startprice"));
        auction.setEndPrice(resultSet.getDouble("endprice"));
        auction.setBuyPrice(resultSet.getDouble("buyprice"));
        auction.setStartTime(resultSet.getDate("starttime"));
        auction.setEndTime(resultSet.getDate("endtime"));
        auction.setStatus(resultSet.getString("status"));

        UserMapper userMapper = new UserMapper();
        User user = userMapper.mapRow(resultSet, i);
        ProductForAuctionMapper productMapper = new ProductForAuctionMapper();
        Product product = productMapper.mapRow(resultSet, i);
        SizeForAuctionMapper sizeMapper = new SizeForAuctionMapper();
        Size size = sizeMapper.mapRow(resultSet, i);

        auction.setAuctionBuyer(user);
        auction.setActionProduct(product);
        auction.setProductSize(size);

        return auction;
    }
}
