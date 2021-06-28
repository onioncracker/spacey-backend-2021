package com.heroku.spacey.mapper.order;

import com.heroku.spacey.dto.order.CheckoutDto;
import com.heroku.spacey.dto.product.ProductCheckoutDto;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AuctionCheckoutMapper implements RowMapper<CheckoutDto> {

    @Override
    public CheckoutDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        CheckoutDto checkoutDto = new CheckoutDto();
        ProductCheckoutDto product = new ProductCheckoutDto();

        product.setProductName(rs.getString("productname"));
        product.setColor(rs.getString("color"));
        product.setSizeName(rs.getString("sizename"));
        product.setPhoto(rs.getString("photo"));
        product.setAmount(rs.getInt("amount"));
        product.setSum(rs.getFloat("finalprice"));

        checkoutDto.getProducts().add(product);

        CheckoutMapper.mapDeliveryInfo(rs, checkoutDto);

        checkoutDto.setOverallPrice(rs.getFloat("finalprice"));

        return checkoutDto;
    }
}
