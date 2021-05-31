package com.heroku.spacey.dao.productDetail;

import com.heroku.spacey.entity.ProductDetails;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDetailMapper implements RowMapper<ProductDetails> {
    @Override
    public ProductDetails mapRow(ResultSet resultSet, int i) throws SQLException {
        var productDetails = new ProductDetails();
        productDetails.setId(resultSet.getInt("id"));
        productDetails.setColor(resultSet.getString("color"));
        productDetails.setSizeProduct(resultSet.getString("sizeproduct"));
        productDetails.setAmount(resultSet.getInt("amount"));
        return productDetails;
    }
}
