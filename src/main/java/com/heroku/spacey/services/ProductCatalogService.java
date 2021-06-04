package com.heroku.spacey.services;

import com.heroku.spacey.dto.product.ProductItemDto;
import com.heroku.spacey.dto.product.ProductPageDto;

import java.sql.SQLException;
import java.util.List;

public interface ProductCatalogService {
    List<ProductPageDto> getAllProduct(String page,
                                       String price,
                                       String categories,
                                       String colors,
                                       String size,
                                       String order) throws SQLException;

    ProductItemDto getProduct(int id) throws SQLException;
}
