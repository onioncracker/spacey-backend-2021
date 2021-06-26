package com.heroku.spacey.mapper.order;

import com.heroku.spacey.dto.order.CheckoutDto;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CheckoutMapper implements RowMapper<CheckoutDto> {

    @Override
    public CheckoutDto mapRow(ResultSet resultSet, int i) throws SQLException {
        CheckoutDto checkoutDto = new CheckoutDto();

        checkoutDto.setOverallPrice(resultSet.getFloat("overallprice"));
        mapDeliveryInfo(resultSet, checkoutDto);

        return checkoutDto;
    }

    public static void mapDeliveryInfo(ResultSet resultSet, CheckoutDto checkoutDto) throws SQLException {
        checkoutDto.setFirstName(resultSet.getString("firstname"));
        checkoutDto.setLastName(resultSet.getString("lastname"));
        checkoutDto.setPhoneNumber(resultSet.getString("phonenumber"));
        checkoutDto.setEmail(resultSet.getString("email"));
        checkoutDto.setCity(resultSet.getString("city"));
        checkoutDto.setStreet(resultSet.getString("street"));
        checkoutDto.setHouse(resultSet.getString("house"));
        checkoutDto.setApartment(resultSet.getString("appartment"));
    }
}
