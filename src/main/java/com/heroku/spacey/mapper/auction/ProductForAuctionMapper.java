package com.heroku.spacey.mapper.auction;

import com.heroku.spacey.entity.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

//TODO: finish product for auction mapper
public class ProductForAuctionMapper implements RowMapper<Product> {
    @Override
    public Product mapRow(ResultSet resultSet, int i) throws SQLException {
        Product product = new Product();
        return product;
    }
}
