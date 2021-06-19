package com.heroku.spacey.mapper.cart;

import com.heroku.spacey.dto.product.ProductForCartDto;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductForCartDtoMapper implements RowMapper<ProductForCartDto> {
    @Override
    public ProductForCartDto mapRow(ResultSet resultSet, int i) throws SQLException {
        ProductForCartDto res = new ProductForCartDto();
        res.setId(resultSet.getLong("productid"));
        res.setName(resultSet.getString("productname"));
        res.setColor(resultSet.getString("color"));
        res.setSize(resultSet.getString("sizename"));
        res.setPhoto(resultSet.getString("photo"));
        res.setAmount(resultSet.getInt("amount"));
        res.setOverallPrice(resultSet.getDouble("sum"));
        return res;
    }
}