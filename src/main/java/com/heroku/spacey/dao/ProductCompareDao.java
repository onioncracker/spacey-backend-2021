package com.heroku.spacey.dao;

import com.heroku.spacey.dto.product.ProductCompareDto;
import com.heroku.spacey.dto.product.ProductItemDto;

import java.sql.SQLException;
import java.util.List;

public interface ProductCompareDao {
    void addToCompare(ProductCompareDto compareDto) throws SQLException;

    void deleteCompare(ProductCompareDto compareDto) throws SQLException;

    List<ProductItemDto> getAllProduct(Long userId) throws SQLException;

    int getCountComparingProduct(Long userId) throws SQLException;

    List<String> getMaterialByProductId(Long productId) throws SQLException;
}
