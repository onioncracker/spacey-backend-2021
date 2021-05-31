package com.heroku.spacey.repositories;

import com.heroku.spacey.dto.ProductCompareDto;
import com.heroku.spacey.dto.ProductDetailDto;
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
@PropertySource(name = "ProductCompare", value = "ProductCompare.properties")
public class ProductCompareImpl implements ProductCompareDao {

    private final JdbcTemplate jdbcTemplate;

    @Value("${getAll}")
    String sqlGetAllProducts;

    @Value("${addToCompare}")
    String sqlAddToCompare;

    @Value("${getCountComparedProducts}")
    String sqlGetCountComparedProducts;

    @Value("${deleteFromComparing}")
    String sqlDeleteFromComparing;

    @Value("${getMaterialByProductId}")
    String sqlGetMaterialByProductId;



    @Override
    public void addToCompare(ProductCompareDto compareDto) {
        jdbcTemplate.update(sqlAddToCompare, compareDto.getUserId(), compareDto.getProductId());
    }

    @Override
    public void deleteCompare(ProductCompareDto compareDto) {
        jdbcTemplate.update(sqlDeleteFromComparing, compareDto.getUserId(), compareDto.getProductId());

    }

    @Override
    public List<ProductDetailDto> getAllProduct(int userId) {
        RowMapper<ProductDetailDto> rowMapper = (rs, i) -> {
            ProductDetailDto productDetailDto = new ProductDetailDto();
            ProductDetailMapper.ProductDetailMapper(rs, productDetailDto);
            productDetailDto.setMaterial((ArrayList<String>) getMaterialByProductId(productDetailDto.getId()));
            return productDetailDto;
        };
        return jdbcTemplate.query(sqlGetAllProducts, rowMapper, userId);
    }


    @Override
    public int getCountComparingProduct(int userId) throws SQLException {
        return jdbcTemplate.queryForObject(sqlGetCountComparedProducts,Integer.class, userId);
    }

    @Override
    public List<String> getMaterialByProductId(int productId) throws SQLException {
        RowMapper<String> rw = (resultSet, i) -> resultSet.getString("namematerial");
        return jdbcTemplate.query(sqlGetMaterialByProductId, rw, productId);
    }

}
