package com.heroku.spacey.mapper.auction.by_id;

import com.heroku.spacey.entity.Auction;
import com.heroku.spacey.entity.Product;
import com.heroku.spacey.entity.Size;
import com.heroku.spacey.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuctionIdMapper implements RowMapper<Auction> {
    @Override
    public Auction mapRow(ResultSet resultSet, int i) throws SQLException {
        Auction auction = new Auction();
        auction.setAuctionId(resultSet.getLong("auctionid"));
        auction.setUserId(resultSet.getLong("userid"));
        auction.setSizeId(resultSet.getLong("sizeid"));
        auction.setAmount(resultSet.getInt("amount"));
        auction.setAuctionName(resultSet.getString("auctionname"));
        auction.setAuctionType(resultSet.getBoolean("auctiontype"));
        auction.setStartPrice(resultSet.getDouble("startprice"));
        auction.setEndPrice(resultSet.getDouble("endprice"));
        auction.setPriceStep(resultSet.getDouble("pricestep"));
        auction.setBuyPrice(resultSet.getDouble("buyprice"));
        auction.setStartTime(resultSet.getTimestamp("starttime"));
        auction.setEndTime(resultSet.getTimestamp("endtime"));
        auction.setStatus(resultSet.getString("status"));

        UserForAuctionIdMapper userMapper = new UserForAuctionIdMapper();
        SizeForAuctionIdMapper sizeMapper = new SizeForAuctionIdMapper();
        ProductForAuctionIdMapper productMapper = new ProductForAuctionIdMapper();


        User user = userMapper.mapRow(resultSet, i);
        Size size = sizeMapper.mapRow(resultSet, i);
        Product product = productMapper.mapRow(resultSet, i);

        auction.setAuctionBuyer(user);
        auction.setProductSize(size);
        auction.setAuctionProduct(product);

        return auction;
    }
}
