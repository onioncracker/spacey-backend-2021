package com.heroku.spacey.dao.impl;

import com.heroku.spacey.dao.ProductToCartDao;
import com.heroku.spacey.dto.cart.ProductForCartDto;
import com.heroku.spacey.entity.Product;
import com.heroku.spacey.entity.ProductToCart;
import com.heroku.spacey.mapper.cart.ProductForCartDtoMapper;
import com.heroku.spacey.mapper.cart.ProductToCartMapper;
import com.heroku.spacey.mapper.product.ProductMapper;
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

    @Value("${get_all_products_for_cart}")
    private String getAllProductsForCart;

    @Value("${get_all_products_for_cart_by_userid}")
    private String getGetAllProductsForCartByUserId;

    public ProductToCartDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insert(Long cartId, Long productId, Long sizeId, int amount, double sum) {
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, cartId);
            ps.setLong(2, productId);
            ps.setLong(3, sizeId);
            ps.setInt(4, amount);
            ps.setDouble(5, sum);
            return ps;
        }, holder);
    }

    @Override
    public ProductToCart get(Long cartId, Long productId, Long sizeId) {
        var result = jdbcTemplate.query(getProductToCart, mapper, cartId, productId, sizeId);
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

    @Override
    public void update(ProductToCart productToCart) {
        Object[] params = new Object[]{
                productToCart.getAmount(),
                productToCart.getSum(),
                productToCart.getCartId(),
                productToCart.getProductId(),
                productToCart.getSizeId()
        };
        Objects.requireNonNull(jdbcTemplate).update(update, params);
    }

    @Override
    public void delete(ProductToCart productToCart) {
        Object[] params = new Object[] {
                productToCart.getCartId(),
                productToCart.getProductId(),
                productToCart.getSizeId()
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

    @Override
    public List<ProductForCartDto> getAllProducts(Long cartId) {
        List<ProductForCartDto> result = jdbcTemplate.query(getAllProductsForCart,
                new ProductForCartDtoMapper(), cartId);
        if (result.isEmpty()) {
            return new ArrayList<>();
        }
        return result;
    }

    @Override
    public List<Product> getAllProductsByUserId(Long userId) {
        List<Product> result = jdbcTemplate.query(getGetAllProductsForCartByUserId,
                new ProductMapper(), userId);
        if (result.isEmpty()) {
            return new ArrayList<>();
        }
        return result;
    }
}
