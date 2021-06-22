package com.heroku.spacey.mapper;

import com.heroku.spacey.dto.order.CheckoutDto;
import org.springframework.jdbc.core.RowMapper;
import com.heroku.spacey.dto.order.ProductCheckoutDto;

import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckoutMapper implements RowMapper<CheckoutDto> {

    @Override
    public CheckoutDto mapRow(ResultSet resultSet, int i) throws SQLException {
        CheckoutDto checkoutDto = new CheckoutDto();
        ProductCheckoutDto product = new ProductCheckoutDto();

        product.setProductName(resultSet.getString("productname"));
        product.setPhoto(resultSet.getString("photo"));
        product.setColor(resultSet.getString("color"));
        product.setSizeName(resultSet.getString("sizename"));
        product.setAmount(resultSet.getInt("amount"));
        product.setSum(resultSet.getFloat("sum"));

        List<ProductCheckoutDto> products = checkoutDto.getProducts();
        products.add(product);

        checkoutDto.setOverallPrice(resultSet.getFloat("overallprice"));
        checkoutDto.setFirstName(resultSet.getString("firstname"));
        checkoutDto.setLastName(resultSet.getString("lastname"));
        checkoutDto.setPhoneNumber(resultSet.getString("phonenumber"));
        checkoutDto.setEmail(resultSet.getString("email"));
        checkoutDto.setCity(resultSet.getString("city"));
        checkoutDto.setStreet(resultSet.getString("street"));
        checkoutDto.setHouse(resultSet.getString("house"));
        checkoutDto.setApartment(resultSet.getString("appartment"));

        return checkoutDto;
    }
}
