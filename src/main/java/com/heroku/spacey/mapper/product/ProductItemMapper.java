package com.heroku.spacey.mapper.product;

import com.heroku.spacey.dto.product.ProductItemDto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductItemMapper {
    public static void productItemMapper(ResultSet rs, ProductItemDto productItemDto) throws SQLException {
        productItemDto.setId(rs.getInt("productid"));
        productItemDto.setName(rs.getString("productname"));
        productItemDto.setSex(rs.getString("productsex"));
        productItemDto.setPrice(rs.getInt("price"));
        productItemDto.setPhoto(rs.getString("photo"));
        productItemDto.setDescription(rs.getString("description"));
        productItemDto.setDiscount(rs.getInt("discount"));
        productItemDto.setAvailability(rs.getBoolean("isavailable"));
        productItemDto.setColor(rs.getString("color"));
        productItemDto.setSize(rs.getString("sizeproduct"));
        productItemDto.setAmount(rs.getInt("amount"));
        productItemDto.setCategory(rs.getString("namecategory"));
    }
}
