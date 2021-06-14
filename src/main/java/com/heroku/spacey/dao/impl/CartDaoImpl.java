package com.heroku.spacey.dao.impl;

import com.heroku.spacey.dao.CartDao;
import com.heroku.spacey.entity.Cart;
import com.heroku.spacey.mapper.cart.CartMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Objects;

@Slf4j
@Repository
@PropertySource("classpath:sql/cart_queries.properties")
public class CartDaoImpl implements CartDao {

    private final JdbcTemplate jdbcTemplate;
    private CartMapper mapper;

    @Value("${cart_exists}")
    private String getCartIdByUSerId;

    @Value("${insert_cart}")
    private String insertCart;

    @Value("${get_cart_by_id}")
    private String getCartById;

    @Value("${update_price}")
    private String updatePrice;

    public CartDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = new CartMapper();
    }

    @Override
    public Long getCartIdByUserId(Long userId) {
        return DataAccessUtils.singleResult(jdbcTemplate.query(getCartIdByUSerId,
            SingleColumnRowMapper.newInstance(Long.class), userId));
    }

    @Override
    public Long createCart(Long userId) {
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertCart, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, userId);
            ps.setInt(2, 0);
            return ps;
        }, holder);
        return (long) Objects.requireNonNull(holder.getKeys()).get("cartId");
    }

    @Override
    public Cart getCart(Long cartId) {
        var result = jdbcTemplate.query(getCartById, mapper, cartId);
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

    @Override
    public void updatePrice(Long cartId, double sum) {
        Object[] params = new Object[]{sum, cartId};
        Objects.requireNonNull(jdbcTemplate).update(updatePrice, params);
    }
}
