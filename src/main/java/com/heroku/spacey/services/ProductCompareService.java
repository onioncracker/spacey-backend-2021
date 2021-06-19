package com.heroku.spacey.services;

import com.heroku.spacey.dto.product.ProductItemDto;

import java.sql.SQLException;
import java.util.List;

public interface ProductCompareService {
    void addProductToCompare(Long productId) throws SQLException;
    List<ProductItemDto> getAllComparingProduct() throws SQLException;
    void deleteProductCompare(Long productId) throws SQLException;
}
