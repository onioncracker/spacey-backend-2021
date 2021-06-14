package com.heroku.spacey.dao.impl;

import com.heroku.spacey.dao.CheckoutDao;
import com.heroku.spacey.dto.order.CheckoutDto;
import com.heroku.spacey.dto.order.ProductCheckoutDto;
import com.heroku.spacey.mapper.CheckoutMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
@PropertySource("classpath:sql/checkout_queries.properties")
public class CheckoutDaoImpl implements CheckoutDao {

    private final JdbcTemplate jdbcTemplate;

    @Value("${select_checkout_by_cart_id}")
    private String sqlSelectCheckoutByCartId;
//    @Value("${update_checkout}")
//    private String sqlUpdateCheckout;


    @Override
    public CheckoutDto getByCartId(Long cartId) {
        ResultSetExtractor<CheckoutDto> rse = resultSet -> {
            CheckoutDto checkoutDto = new CheckoutDto();
            CheckoutMapper.mapCheckout(resultSet, checkoutDto);

            return checkoutDto;
        };

        return jdbcTemplate.query(sqlSelectCheckoutByCartId, rse, cartId);
    }

//    @Override
//    public int update(CheckoutDto checkoutDto) {
//        List<ProductCheckoutDto> products = new ArrayList<>();
//
//        float overallPrice = checkoutDto.getOverallPrice();
//        String firstname = checkoutDto.getFirstName();
//        String lastname = checkoutDto.getLastName();
//        String phoneNumber = checkoutDto.getPhoneNumber();
//        String email = checkoutDto.getEmail();
//        String city = checkoutDto.getCity();
//        String street = checkoutDto.getStreet();
//        String house = checkoutDto.getHouse();
//        String apartment = checkoutDto.getApartment();
//
//        Object[] parameters = new Object[] {email, userrole, firstname, lastname, status, phonenumber, loginid};
//
//        return jdbcTemplate.update(sqlUpdateCheckout, parameters);;
//    }
}
