package com.heroku.spacey.dao.impl;

import com.heroku.spacey.dao.ProductDetailDao;
import com.heroku.spacey.entity.ProductDetail;
import com.heroku.spacey.mapper.ProductDetailMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Objects;

@Slf4j
@Repository
@PropertySource("classpath:sql/product_detail_queries.properties")
public class ProductDetailDaoImpl implements ProductDetailDao {
    private final ProductDetailMapper mapper = new ProductDetailMapper();
    private final JdbcTemplate jdbcTemplate;

    @Value("${product_detail_get_by_id}")
    private String getProductDetailById;
    @Value("${insert_product_detail}")
    private String editProductDetail;
    @Value("${update_product_detail}")
    private String updateProductDetail;
    @Value("${delete_product_detail}")
    private String deleteProductDetail;

    public ProductDetailDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public ProductDetail getById(int id) {
        return Objects.requireNonNull(jdbcTemplate).queryForObject(getProductDetailById, mapper, id);
    }

    @Override
    public int insert(ProductDetail productDetail) {
        int returnId;
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(editProductDetail, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, productDetail.getProductId());
            ps.setString(2, productDetail.getColor());
            ps.setString(3, productDetail.getSizeProduct());
            ps.setInt(4, productDetail.getAmount());
            return ps;
        }, holder);

        if (holder.getKeys().size() > 1) {
            returnId = (int) holder.getKeys().get("detailsId");
        } else {
            returnId = holder.getKey().intValue();
        }
        return returnId;
    }

    @Override
    public void update(ProductDetail productDetail) {
        var params = new Object[]{
                productDetail.getColor(), productDetail.getSizeProduct(),
                productDetail.getAmount(), productDetail.getId()
        };
        Objects.requireNonNull(jdbcTemplate).update(updateProductDetail, params);
    }

    @Override
    public void delete(int id) {
        Objects.requireNonNull(jdbcTemplate).update(deleteProductDetail, id);
    }
}
