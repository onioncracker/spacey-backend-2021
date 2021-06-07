package com.heroku.spacey.mapper;

import com.heroku.spacey.entity.ProductDetail;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDetailsForProductMapper implements RowMapper<ProductDetail> {
    @Override
    public ProductDetail mapRow(ResultSet resultSet, int i) throws SQLException {
        ProductDetail productDetails = new ProductDetail();
        productDetails.setId(resultSet.getLong("pd_id"));
        productDetails.setProductId(resultSet.getLong("pd_product_id"));
        productDetails.setColor(resultSet.getString("pd_color"));
        productDetails.setSizeProduct(resultSet.getString("pd_size"));
        productDetails.setAmount(resultSet.getLong("pd_amount"));
        return productDetails;
    }
}
