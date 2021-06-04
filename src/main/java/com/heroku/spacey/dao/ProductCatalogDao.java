package com.heroku.spacey.dao;

import com.heroku.spacey.dto.product.ProductItemDto;
import com.heroku.spacey.dto.product.ProductPageDto;

import java.sql.SQLException;
import java.util.List;

public interface ProductCatalogDao {
    ProductItemDto getProductById(int id) throws SQLException;

    List<ProductPageDto> getAllProduct(String[] categories,
                                       Integer[] prices,
                                       String[] colors,
                                       String[] size,
                                       int pageNum,
                                       int pageSize,
                                       String order) throws SQLException;
}
