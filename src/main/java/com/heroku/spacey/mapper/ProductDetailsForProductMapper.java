package com.heroku.spacey.mapper;

import com.heroku.spacey.entity.ProductDetail;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDetailsForProductMapper implements RowMapper<ProductDetail> {
    @Override
    public ProductDetail mapRow(ResultSet resultSet, int i) throws SQLException {
        var productDetails = new ProductDetail();
        productDetails.setId(resultSet.getInt("pd_id"));
        productDetails.setProductId(resultSet.getInt("pd_product_id"));
        productDetails.setColor(resultSet.getString("pd_color"));
        productDetails.setSizeProduct(resultSet.getString("pd_size"));
        productDetails.setAmount(resultSet.getInt("pd_amount"));
        return productDetails;
    }
}
