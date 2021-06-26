package com.heroku.spacey.mapper.auction;

import com.heroku.spacey.dto.auction.AuctionDto;
import com.heroku.spacey.dto.auction.AuctionProductDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuctionMapper implements RowMapper<AuctionDto> {
    @Override
    public AuctionDto mapRow(ResultSet resultSet, int i) throws SQLException {
        AuctionDto auction = new AuctionDto();
        auction.setAuctionId(resultSet.getLong("auctionid"));
        auction.setUserId(resultSet.getLong("userid"));
        auction.setSizeName(resultSet.getString("sizename"));
        auction.setAmount(resultSet.getInt("amount"));
        auction.setAuctionName(resultSet.getString("auctionname"));
        auction.setAuctionType(resultSet.getBoolean("auctiontype"));
        auction.setStartPrice(resultSet.getDouble("startprice"));
        auction.setEndPrice(resultSet.getDouble("endprice"));
        auction.setBuyPrice(resultSet.getDouble("buyprice"));
        auction.setStartTime(resultSet.getDate("starttime"));
        auction.setEndTime(resultSet.getDate("endtime"));
        auction.setStatus(resultSet.getString("status"));

        ProductForAuctionMapper productMapper = new ProductForAuctionMapper();
        AuctionProductDto product = productMapper.mapRow(resultSet, i);

        auction.setProduct(product);

        return auction;
    }
}
