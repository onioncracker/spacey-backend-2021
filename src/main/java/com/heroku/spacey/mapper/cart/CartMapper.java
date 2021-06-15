package com.heroku.spacey.mapper.cart;

import com.heroku.spacey.entity.Cart;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CartMapper implements RowMapper<Cart> {
    @Override
    public Cart mapRow(ResultSet resultSet, int i) throws SQLException {
        Cart cart = new Cart();
        cart.setCartId(resultSet.getLong("cartid"));
        cart.setUserId(resultSet.getLong("userid"));
        cart.setOverallPrice(resultSet.getDouble("overallprice"));
        return cart;
    }
}