package com.heroku.spacey.dao.impl;

import com.heroku.spacey.dao.ProductToCartDao;
import com.heroku.spacey.entity.ProductToCart;
import com.heroku.spacey.mapper.ProductToCartMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Repository
@PropertySource("classpath:sql/product_to_cart_queries.properties")
public class ProductToCartDaoImpl implements ProductToCartDao {

    private final JdbcTemplate jdbcTemplate;
    private final ProductToCartMapper mapper = new ProductToCartMapper();

    @Value("${insert_product_to_cart}")
    private String insert;

    @Value("${get_product_to_cart}")
    private String getProductToCart;

    @Value("${get_all_by_cartid}")
    private String getAllByCartId;

    @Value("${update_product_to_cart}")
    private String update;

    @Value("${delete_product_to_cart}")
    private String delete;

    public ProductToCartDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insert(Long cartId, Long productId, int amount, double sum) {
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, cartId);
            ps.setLong(2, productId);
            ps.setInt(3, amount);
            ps.setDouble(4, sum);
            return ps;
        }, holder);
    }

    @Override
    public ProductToCart get(Long cartId, Long productId) {
        var result = jdbcTemplate.query(getProductToCart, mapper, cartId, productId);
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

    @Override
    public void update(ProductToCart productToCart) {
        Object[] params = new Object[]{
            productToCart.getAmount(), productToCart.getSum(),
            productToCart.getCartId(), productToCart.getProductId()
        };
        Objects.requireNonNull(jdbcTemplate).update(update, params);
    }

    @Override
    public void delete(ProductToCart productToCart) {
        Object[] params = new Object[] {
            productToCart.getCartId(),
            productToCart.getProductId()
        };
        Objects.requireNonNull(jdbcTemplate).update(delete, params);
    }

    @Override
    public List<ProductToCart> getAllInCart(Long cartId) {
        List<ProductToCart> result = jdbcTemplate.query(getAllByCartId, mapper, cartId);
        if (result.isEmpty()) {
            return new ArrayList<>();
        }
        return result;
    }
}
