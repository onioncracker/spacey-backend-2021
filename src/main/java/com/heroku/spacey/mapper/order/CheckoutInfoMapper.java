package com.heroku.spacey.mapper.order;

import com.heroku.spacey.dto.order.CheckoutDto;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CheckoutInfoMapper implements RowMapper<CheckoutDto> {

    @Override
    public CheckoutDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        CheckoutDto checkoutInfo = new CheckoutDto();

        checkoutInfo.setFirstName(rs.getString("firstname"));
        checkoutInfo.setLastName(rs.getString("lastname"));
        checkoutInfo.setPhoneNumber(rs.getString("phonenumber"));
        checkoutInfo.setEmail(rs.getString("email"));
        checkoutInfo.setCity(rs.getString("city"));
        checkoutInfo.setStreet(rs.getString("street"));
        checkoutInfo.setHouse(rs.getString("house"));
        checkoutInfo.setApartment(rs.getString("appartment"));
        checkoutInfo.setOverallPrice(rs.getFloat("overallprice"));

        return checkoutInfo;
    }
}
