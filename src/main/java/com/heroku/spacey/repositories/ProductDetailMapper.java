package com.heroku.spacey.repositories;

import com.heroku.spacey.dto.ProductDetailDto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDetailMapper {
    static void ProductDetailMapper(ResultSet rs, ProductDetailDto productDetailDto) throws SQLException {
        productDetailDto.setId(rs.getInt("productid"));
        productDetailDto.setName(rs.getString("name"));
        productDetailDto.setSex(rs.getString("productsex"));
        productDetailDto.setPrice(rs.getInt("price"));
        productDetailDto.setPhoto(rs.getString("photo"));
        productDetailDto.setDescription(rs.getString("description"));
        productDetailDto.setDiscount(rs.getInt("discount"));
        productDetailDto.setAvailability(rs.getBoolean("isavailable"));
        productDetailDto.setColor(rs.getString("color"));
        productDetailDto.setSize(rs.getString("sizeproduct"));
        productDetailDto.setAmount(rs.getInt("amount"));
        productDetailDto.setCategory(rs.getString("namecategory"));
    }
}
