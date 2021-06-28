package com.heroku.spacey.mapper.auction.all;

import com.heroku.spacey.entity.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductForAuctionAllMapper implements RowMapper<Product> {
    @Override
    public Product mapRow(ResultSet resultSet, int i) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getLong("productid"));
        product.setPhoto(resultSet.getString("photo"));

        return product;
    }
}
