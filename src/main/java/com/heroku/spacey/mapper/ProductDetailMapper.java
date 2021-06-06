package com.heroku.spacey.mapper;

import com.heroku.spacey.entity.ProductDetail;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDetailMapper implements RowMapper<ProductDetail> {
    @Override
    public ProductDetail mapRow(ResultSet resultSet, int i) throws SQLException {
        ProductDetail productDetails = new ProductDetail();
        productDetails.setId(resultSet.getLong("detailsid"));
        productDetails.setProductId(resultSet.getLong("productid"));
        productDetails.setColor(resultSet.getString("color"));
        productDetails.setSizeProduct(resultSet.getString("sizeproduct"));
        productDetails.setAmount(resultSet.getLong("amount"));
        return productDetails;
    }
}
