package com.heroku.spacey.mapper.cart;

import com.heroku.spacey.dto.cart.ProductForUnauthorizedCart;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductForUnauthorizedCartMapper implements RowMapper<ProductForUnauthorizedCart> {
    @Override
    public ProductForUnauthorizedCart mapRow(ResultSet resultSet, int i) throws SQLException {
        ProductForUnauthorizedCart res = new ProductForUnauthorizedCart();
        res.setSizeId(resultSet.getLong("sizeid"));
        res.setId(resultSet.getLong("productid"));
        res.setName(resultSet.getString("productname"));
        res.setColor(resultSet.getString("color"));
        res.setSize(resultSet.getString("sizename"));
        res.setPhoto(resultSet.getString("photo"));
        res.setQuantity(resultSet.getInt("quantity"));
        res.setPrice(resultSet.getDouble("price"));
        res.setDiscount(resultSet.getDouble("discount"));
        return res;
    }

}
