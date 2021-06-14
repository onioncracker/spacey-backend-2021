package com.heroku.spacey.dao.impl;

import com.heroku.spacey.dao.CheckoutDao;
import com.heroku.spacey.dto.order.CheckoutDto;
import com.heroku.spacey.mapper.CheckoutMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@PropertySource("classpath:sql/checkout_queries.properties")
public class CheckoutDaoImpl implements CheckoutDao {

    private final JdbcTemplate jdbcTemplate;

    @Value("${select_checkout_by_cart_id}")
    private String sqlSelectCheckoutByCartId;


    @Override
    public CheckoutDto getByCartId(Long cartId) {
        ResultSetExtractor<CheckoutDto> rse = resultSet -> {
            CheckoutDto checkoutDto = new CheckoutDto();
            CheckoutMapper.mapCheckout(resultSet, checkoutDto);

            return checkoutDto;
        };

        return jdbcTemplate.query(sqlSelectCheckoutByCartId, rse, cartId);
    }
}
