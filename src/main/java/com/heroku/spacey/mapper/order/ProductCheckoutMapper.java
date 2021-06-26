package com.heroku.spacey.mapper.order;

import com.heroku.spacey.dto.product.ProductCheckoutDto;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ProductCheckoutMapper implements RowMapper<ProductCheckoutDto> {

    @Override
    public ProductCheckoutDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        ProductCheckoutDto productCheckoutDto = new ProductCheckoutDto();

        productCheckoutDto.setProductName(rs.getString("productname"));
        productCheckoutDto.setColor(rs.getString("color"));
        productCheckoutDto.setSizeName(rs.getString("sizename"));
        productCheckoutDto.setPhoto(rs.getString("photo"));
        productCheckoutDto.setAmount(rs.getInt("amount"));
        productCheckoutDto.setSum(rs.getFloat("sum"));

        return productCheckoutDto;
    }
}
