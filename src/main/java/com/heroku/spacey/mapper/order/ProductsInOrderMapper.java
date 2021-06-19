package com.heroku.spacey.mapper.order;

import com.heroku.spacey.dto.product.ProductOrderDto;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ProductsInOrderMapper implements RowMapper<ProductOrderDto> {

    @Override
    public ProductOrderDto mapRow(ResultSet resultSet, int i) throws SQLException {
        ProductOrderDto product = new ProductOrderDto();

        product.setId(resultSet.getLong("productid"));
        product.setName(resultSet.getString("productname"));
        product.setColor(resultSet.getString("color"));
        product.setSize(resultSet.getString("sizename"));
        product.setPrice(resultSet.getFloat("price"));
        product.setAmount(resultSet.getInt("amount"));
        product.setPhoto(resultSet.getString("photo"));

        return product;
    }
}
