package com.heroku.spacey.services;

import com.heroku.spacey.dto.product.ProductItemDto;
import com.heroku.spacey.dto.product.ProductPageDto;

import java.sql.SQLException;
import java.util.List;

public interface ProductCatalogService {
    List<ProductPageDto> getAllProduct(Integer pageNum,
                                       Integer pageSize,
                                       String sex,
                                       String price,
                                       String categories,
                                       String colors,
                                       String order) throws SQLException;

    ProductItemDto getProduct(Long id) throws SQLException;
}
