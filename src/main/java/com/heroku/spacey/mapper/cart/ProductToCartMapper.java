package com.heroku.spacey.mapper.cart;

import com.heroku.spacey.entity.ProductToCart;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductToCartMapper implements RowMapper<ProductToCart> {
    @Override
    public ProductToCart mapRow(ResultSet resultSet, int i) throws SQLException {
        ProductToCart res = new ProductToCart();
        res.setCartId(resultSet.getLong("cartid"));
        res.setProductId(resultSet.getLong("productid"));
        res.setAmount(resultSet.getInt("amount"));
        res.setSum(resultSet.getDouble("sum"));
        return res;
    }
}
