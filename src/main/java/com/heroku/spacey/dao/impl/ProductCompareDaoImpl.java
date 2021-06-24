package com.heroku.spacey.dao.impl;

import com.heroku.spacey.dao.ProductCompareDao;
import com.heroku.spacey.dto.product.ProductItemDto;
import com.heroku.spacey.dto.product.SizeDto;
import com.heroku.spacey.mapper.SizeAvailableMapper;
import com.heroku.spacey.mapper.product.ProductItemMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
@PropertySource("classpath:sql/product_compare_queries.properties")
public class ProductCompareDaoImpl implements ProductCompareDao {

    private final JdbcTemplate jdbcTemplate;
    private final SizeAvailableMapper sizeAvailableMapper;

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

    @Value("${get_sizes_by_id}")
    private String sqlGetSizesByProductId;

    @Value("${is_product_compared}")
    private String sqlIsProductCompered;


    @Override
    public void addToCompare(Long userId, Long productId) {
        jdbcTemplate.update(sqlAddToCompare, userId, productId);
    }

    @Override
    public void deleteCompare(Long userId, Long productId) {
        jdbcTemplate.update(sqlDeleteFromComparing, userId, productId);

    }

    @Override
    public List<ProductItemDto> getAllProduct(Long userId) {
        RowMapper<ProductItemDto> rowMapper = (rs, i) -> {
            ProductItemDto productItemDto = new ProductItemDto();
            ProductItemMapper.productItemMapper(rs, productItemDto);
            productItemDto.setMaterials((ArrayList<String>) getMaterialByProductId(productItemDto.getId()));
            productItemDto.setSizes((ArrayList<SizeDto>) getSizesByProductId(productItemDto.getId()));
            return productItemDto;
        };
        return jdbcTemplate.query(sqlGetAllProducts, rowMapper, userId);
    }


    @Override
    public Integer getCountComparingProduct(Long userId) throws SQLException {
        return jdbcTemplate.queryForObject(sqlGetCountComparedProducts, Integer.class, userId);
    }

    @Override
    public List<String> getMaterialByProductId(Long productId) throws SQLException {
        RowMapper<String> rw = (resultSet, i) -> resultSet.getString("namematerial");
        return jdbcTemplate.query(sqlGetMaterialByProductId, rw, productId);
    }

    @Override
    public Boolean isProductAlreadyCompared(Long userId, Long productId) {
        ResultSetExtractor<Boolean> rs = ResultSet::next;
        return jdbcTemplate.query(sqlIsProductCompered, rs, userId, productId);
    }

    public List<SizeDto> getSizesByProductId(Long id) {
        return jdbcTemplate.query(sqlGetSizesByProductId, sizeAvailableMapper, id);
    }

}
