package com.heroku.spacey.mapper.auction.all;

import com.heroku.spacey.entity.Auction;
import com.heroku.spacey.entity.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuctionAllMapper implements RowMapper<Auction> {
    @Override
    public Auction mapRow(ResultSet resultSet, int i) throws SQLException {
        Auction auction = new Auction();
        auction.setAuctionId(resultSet.getLong("auctionid"));
        auction.setProductId(resultSet.getLong("productid"));
        auction.setAmount(resultSet.getInt("amount"));
        auction.setAuctionName(resultSet.getString("auctionname"));
        auction.setAuctionType(resultSet.getBoolean("auctiontype"));
        auction.setStartPrice(resultSet.getDouble("startprice"));
//        auction.setStartTime(resultSet.getTimestamp("starttime"));

        ProductForAuctionAllMapper productMapper = new ProductForAuctionAllMapper();
        Product product = productMapper.mapRow(resultSet, i);

        auction.setAuctionProduct(product);
        return auction;
    }
}
