package com.heroku.spacey.dao.product;

import com.heroku.spacey.entity.ProductDetails;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDetailsForProductMapper implements RowMapper<ProductDetails> {
    @Override
    public ProductDetails mapRow(ResultSet resultSet, int i) throws SQLException {
        var productDetails = new ProductDetails();
        productDetails.setId(resultSet.getInt("pd_id"));
        productDetails.setProductId(resultSet.getInt("pd_product_id"));
        productDetails.setColor(resultSet.getString("pd_color"));
        productDetails.setSizeProduct(resultSet.getString("pd_size"));
        productDetails.setAmount(resultSet.getInt("pd_amount"));
        return productDetails;
    }
}
