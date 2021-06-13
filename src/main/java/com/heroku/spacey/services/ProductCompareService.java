package com.heroku.spacey.services;

import com.heroku.spacey.dto.product.ProductCompareDto;
import com.heroku.spacey.dto.product.ProductItemDto;

import java.sql.SQLException;
import java.util.List;

public interface ProductCompareService {
    void addProductToCompare(ProductCompareDto compareDto) throws SQLException;
    List<ProductItemDto> getAllComparingProduct(Long userId) throws SQLException;
    void deleteProductCompare(ProductCompareDto compareDto) throws SQLException;
}
