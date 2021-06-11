package com.heroku.spacey.dao.impl;

import com.heroku.spacey.dao.ProductCatalogDao;
import com.heroku.spacey.dao.common.ProductCatalogQueryAdapter;
import com.heroku.spacey.dto.product.ProductItemDto;
import com.heroku.spacey.dto.product.ProductPageDto;
import com.heroku.spacey.dto.product.SizeDto;
import com.heroku.spacey.mapper.ProductItemMapper;
import com.heroku.spacey.mapper.SizeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.webjars.NotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
@PropertySource("classpath:sql/product_catalog_queries.properties")
public class ProductCatalogImpl implements ProductCatalogDao {

    private final JdbcTemplate jdbcTemplate;
    private final ProductCatalogQueryAdapter productCatalogQueryAdapter;
    private final SizeMapper sizeMapper;

    @Value("${get_all_products}")
    private String sqlGetAll;
    @Value("${get_product_by_id}")
    private String sqlGetById;
    @Value("${get_categories_by_id}")
    private String sqlGetCategoriesById;
    @Value("${get_sizes_by_id}")
    private String sqlGetSizesById;


    @Override
    public ProductItemDto getProductById(Long id) throws SQLException {
        ProductItemDto productItemDto = new ProductItemDto();
        ResultSetExtractor<ProductItemDto> rse = rs -> {
            if (!rs.next()) {
                throw new NotFoundException("Item not found");
            }
            ProductItemMapper.productItemMapper(rs, productItemDto);
            productItemDto.setMaterials((ArrayList<String>) getMaterials(id));
            productItemDto.setSizes((ArrayList<SizeDto>) getSizes(id));
            return productItemDto;
        };

        return jdbcTemplate.query(sqlGetById, rse, id);
    }

    @Override
    public List<ProductPageDto> getAllProduct(String[] categories,
                                              Integer[] prices,
                                              String sex,
                                              String[] colors,
                                              Integer pageNum,
                                              Integer pageSize,
                                              String order) throws SQLException {

        productCatalogQueryAdapter
                .createSelect(sqlGetAll)
                .addFilters(categories, sex, prices, colors)
                .addOrdering(order)
                .setPage(pageNum, pageSize);

        RowMapper<ProductPageDto> rowMapper = (resultSet, i) -> {
            ProductPageDto productDto = new ProductPageDto();
            productDto.setId(resultSet.getInt("productid"));
            productDto.setName(resultSet.getString("productname"));
            productDto.setPhoto(resultSet.getString("photo"));
            productDto.setPrice(resultSet.getString("price"));
            return productDto;
        };

        return jdbcTemplate.query(productCatalogQueryAdapter.build(), rowMapper, productCatalogQueryAdapter.getParams().toArray());
    }

    private List<String> getMaterials(Long id) {
        RowMapper<String> rowMapper = (resultSet, i) ->
                resultSet.getString("namematerial");
        return jdbcTemplate.query(sqlGetCategoriesById, rowMapper, id);
    }

    private List<SizeDto> getSizes(Long id) {
        return jdbcTemplate.query(sqlGetSizesById, sizeMapper, id);
    }
}
