package com.heroku.spacey.dao;

import com.heroku.spacey.dto.product.ProductItemDto;
import com.heroku.spacey.dto.product.ProductPageDto;

import java.sql.SQLException;
import java.util.List;

public interface ProductCatalogDao {
    ProductItemDto getProductById(Long id) throws SQLException;

    List<ProductPageDto> getAllProduct(String prompt,
                                       String[] categories,
                                       Integer[] prices,
                                       String sex,
                                       String[] colors,
                                       Integer pageNum,
                                       Integer pageSize,
                                       String order) throws SQLException;
}
