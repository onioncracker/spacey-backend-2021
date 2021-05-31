package com.heroku.spacey.repositories;

import com.heroku.spacey.dto.ProductDetailDto;
import com.heroku.spacey.dto.ProductPageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
@PropertySource(name = "ProductCatalog", value = "ProductCatalog.properties")
public class ProductCatalogImpl implements ProductCatalogDao {

    private final JdbcTemplate jdbcTemplate;
    private final ProductCatalogQueryAdapter productCatalogQueryAdapter;

    @Value("${getAll}")
    private String sqlGetAll;
    @Value("${getById}")
    private String sqlDetById;



    @Override
    public ProductDetailDto getProductById(int id) throws SQLException {
        ProductDetailDto productDetailDto = new ProductDetailDto();
        ResultSetExtractor<ProductDetailDto> rse = rs -> {
            ArrayList<String> materials = productDetailDto.getMaterial();
            while (rs.next()) {
                if (materials == null){
                    materials = new ArrayList<>();
                }
                ProductDetailMapper.ProductDetailMapper(rs, productDetailDto);
                materials.add(rs.getString("namematerial"));
            }
            productDetailDto.setMaterial(materials);
            return productDetailDto;
        };

        return jdbcTemplate.query(sqlDetById, rse, id);
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
            productDto.setName(resultSet.getString("name"));
            productDto.setPhoto(resultSet.getString("photo"));
            productDto.setPrice(resultSet.getString("price"));
            return productDto;
        };

        return jdbcTemplate.query(productCatalogQueryAdapter.build(), rowMapper, productCatalogQueryAdapter.getParams().toArray());
    }
}
