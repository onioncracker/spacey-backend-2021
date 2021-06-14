package com.heroku.spacey.dao.impl;

import com.heroku.spacey.dao.ProductToCartDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Slf4j
@Repository
@PropertySource("classpath:sql/product_to_cart_queries.properties")
public class ProductToCartDaoImpl implements ProductToCartDao {

    private final JdbcTemplate jdbcTemplate;

    @Value("${insert_product_to_cart}")
    private String insert;

    public ProductToCartDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insert(Long cartId, Long userId, int amount, double sum) {
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, cartId);
            ps.setLong(2, userId);
            ps.setInt(3, amount);
            ps.setDouble(4, sum);
            return ps;
        }, holder);
    }
}
