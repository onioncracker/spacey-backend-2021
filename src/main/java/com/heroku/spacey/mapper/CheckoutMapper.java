package com.heroku.spacey.mapper;

import com.heroku.spacey.dto.order.CheckoutDto;
import com.heroku.spacey.dto.order.ProductCheckoutDto;
import org.webjars.NotFoundException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckoutMapper {
    public static void mapCheckout(ResultSet resultSet, CheckoutDto checkoutDto) throws SQLException {
        if (!resultSet.next()) {
            throw new NotFoundException("Haven't found checkout info with such ID.");
        } else {
            ProductCheckoutDto product = new ProductCheckoutDto();

            product.setProductName(resultSet.getString("productname"));
            product.setColor(resultSet.getString("color"));
            product.setSizeName(resultSet.getString("sizename"));
            product.setAmount(resultSet.getInt("amount"));
            product.setSum(resultSet.getFloat("sum"));

            checkoutDto.getProducts().add(product);

            checkoutDto.setOverallPrice(resultSet.getFloat("overallprice"));
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
}
