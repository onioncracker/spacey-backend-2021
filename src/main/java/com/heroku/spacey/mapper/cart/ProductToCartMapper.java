package com.heroku.spacey.mapper.cart;

import com.heroku.spacey.entity.ProductToCart;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class ProductToCartMapper implements RowMapper<ProductToCart> {
    @Override
    public ProductToCart mapRow(ResultSet resultSet, int i) throws SQLException {
        ProductToCart res = new ProductToCart();
        res.setCartId(resultSet.getLong("cartid"));
        res.setProductId(resultSet.getLong("productid"));
        res.setSizeId(resultSet.getLong("sizeid"));
        res.setAmount(resultSet.getInt("amount"));
        res.setSum(resultSet.getDouble("sum"));
        res.setAddDateTime(resultSet.getObject("add_date", LocalDateTime.class));
        return res;
    }
}
