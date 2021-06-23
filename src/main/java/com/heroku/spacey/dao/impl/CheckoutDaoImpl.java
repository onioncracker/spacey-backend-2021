package com.heroku.spacey.dao.impl;

import org.webjars.NotFoundException;
import lombok.RequiredArgsConstructor;
import com.heroku.spacey.dao.CheckoutDao;
import com.heroku.spacey.dto.order.CheckoutDto;
import com.heroku.spacey.mapper.CheckoutMapper;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
@PropertySource("classpath:sql/checkout_queries.properties")
public class CheckoutDaoImpl implements CheckoutDao {
    private final CheckoutMapper mapper = new CheckoutMapper();
    private final JdbcTemplate jdbcTemplate;

    @Value("${select_checkout_by_cart_id}")
    private String sqlSelectCheckoutByCartId;

    @Value("${select_checkout_by_user_id}")
    private String sqlSelectCheckoutByUserId;


    @Override
    public CheckoutDto getByCartId(Long cartId) {
        List<CheckoutDto> checkoutDtos = Objects.requireNonNull(jdbcTemplate).query(sqlSelectCheckoutByCartId, mapper, cartId);

        if (checkoutDtos.isEmpty()) {
            throw new NotFoundException("Haven't found checkout info for such cart.");
        }

        return checkoutDtos.get(0);
    }

    @Override
    public CheckoutDto getByUserId(Long userId) {
        List<CheckoutDto> checkoutDtos = Objects.requireNonNull(jdbcTemplate).query(sqlSelectCheckoutByUserId, mapper, userId);

        if (checkoutDtos.isEmpty()) {
            throw new NotFoundException("Haven't found checkout info for such user.");
        }

        return checkoutDtos.get(0);
    }
}
