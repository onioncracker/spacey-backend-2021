package com.heroku.spacey.mapper.order;

import com.heroku.spacey.dto.order.CheckoutDto;
import com.heroku.spacey.dto.order.ProductCheckoutDto;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AuctionCheckoutMapper implements RowMapper<CheckoutDto> {

    @Override
    public CheckoutDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        // TODO: remove duplications
        CheckoutDto checkoutDto = new CheckoutDto();
        ProductCheckoutDto product = new ProductCheckoutDto();

        product.setProductName(rs.getString("productname"));
        product.setColor(rs.getString("color"));
        product.setSizeName(rs.getString("sizename"));
        product.setPhoto(rs.getString("photo"));
        product.setAmount(rs.getInt("amount"));
        product.setSum(rs.getFloat("finalprice"));

        checkoutDto.getProducts().add(product);

        checkoutDto.setFirstName(rs.getString("firstname"));
        checkoutDto.setLastName(rs.getString("lastname"));
        checkoutDto.setPhoneNumber(rs.getString("phonenumber"));
        checkoutDto.setEmail(rs.getString("email"));
        checkoutDto.setCity(rs.getString("city"));
        checkoutDto.setStreet(rs.getString("street"));
        checkoutDto.setHouse(rs.getString("house"));
        checkoutDto.setApartment(rs.getString("appartment"));

        checkoutDto.setOverallPrice(rs.getFloat("finalprice"));

        return checkoutDto;
    }
}
