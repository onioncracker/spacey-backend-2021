package com.heroku.spacey.dao.impl;

import com.heroku.spacey.dao.ProductCatalogDao;
import com.heroku.spacey.dao.common.ProductCatalogQueryAdapter;
import com.heroku.spacey.dto.product.ProductItemDto;
import com.heroku.spacey.dto.product.ProductPageDto;
import com.heroku.spacey.mapper.product.ProductItemMapper;
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

    @Value("${get_all_products}")
    private String sqlGetAll;
    @Value("${get_product_by_id}")
    private String sqlGetById;


    @Override
    public ProductItemDto getProductById(int id) throws SQLException {
        ProductItemDto productItemDto = new ProductItemDto();
        ResultSetExtractor<ProductItemDto> rse = rs -> {
            ArrayList<String> materials = productItemDto.getMaterial();
            if (!rs.next()) {
                throw new NotFoundException("Item not found");
            }
            while (rs.next()) {
                if (materials == null) {
                    materials = new ArrayList<>();
                }
                ProductItemMapper.productItemMapper(rs, productItemDto);
                materials.add(rs.getString("namematerial"));
            }
            productItemDto.setMaterial(materials);
            return productItemDto;
        };

        return jdbcTemplate.query(sqlGetById, rse, id);
    }

    @Override
    public List<ProductPageDto> getAllProduct(String[] categories,
                                              Integer[] prices,
                                              String[] colors,
                                              String[] sizes,
                                              int pageNum,
                                              int pageSize,
                                              String order) throws SQLException {

        productCatalogQueryAdapter
                .createSelect(sqlGetAll)
                .addFilters(categories, prices, colors, sizes)
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
}
