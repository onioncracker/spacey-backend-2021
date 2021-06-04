package com.heroku.spacey.dao.impl;

import com.heroku.spacey.dao.ProductCompareDao;
import com.heroku.spacey.dto.product.ProductCompareDto;
import com.heroku.spacey.dto.product.ProductItemDto;
import com.heroku.spacey.mapper.ProductItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
@PropertySource("classpath:sql/product_compare_queries.properties")
public class ProductCompareImpl implements ProductCompareDao {

    private final JdbcTemplate jdbcTemplate;

    @Value("${get_all_compared_products}")
    private String sqlGetAllProducts;

    @Value("${add_to_compare}")
    private String sqlAddToCompare;

    @Value("${get_count_compared_products}")
    private String sqlGetCountComparedProducts;

    @Value("${delete_from_comparing}")
    private String sqlDeleteFromComparing;

    @Value("${get_material_by_product_id}")
    private String sqlGetMaterialByProductId;


    @Override
    public void addToCompare(ProductCompareDto compareDto) {
        jdbcTemplate.update(sqlAddToCompare, compareDto.getUserId(), compareDto.getProductId());
    }

    @Override
    public void deleteCompare(ProductCompareDto compareDto) {
        jdbcTemplate.update(sqlDeleteFromComparing, compareDto.getUserId(), compareDto.getProductId());

    }

    @Override
    public List<ProductItemDto> getAllProduct(int userId) {
        RowMapper<ProductItemDto> rowMapper = (rs, i) -> {
            ProductItemDto productItemDto = new ProductItemDto();
            ProductItemMapper.productItemMapper(rs, productItemDto);
            productItemDto.setMaterial((ArrayList<String>) getMaterialByProductId(productItemDto.getId()));
            return productItemDto;
        };
        return jdbcTemplate.query(sqlGetAllProducts, rowMapper, userId);
    }


    @Override
    public int getCountComparingProduct(int userId) throws SQLException {
        return jdbcTemplate.queryForObject(sqlGetCountComparedProducts, Integer.class, userId);
    }

    @Override
    public List<String> getMaterialByProductId(int productId) throws SQLException {
        RowMapper<String> rw = (resultSet, i) -> resultSet.getString("namematerial");
        return jdbcTemplate.query(sqlGetMaterialByProductId, rw, productId);
    }

}
