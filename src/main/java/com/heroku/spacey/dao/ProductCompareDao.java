package com.heroku.spacey.dao;

import com.heroku.spacey.dto.product.ProductItemDto;

import java.sql.SQLException;
import java.util.List;

public interface ProductCompareDao {
    void addToCompare(Long userId, Long productId) throws SQLException;

    void deleteCompare(Long userId, Long productId) throws SQLException;

    List<ProductItemDto> getAllProduct(Long userId) throws SQLException;

    Integer getCountComparingProduct(Long userId) throws SQLException;

    List<String> getMaterialByProductId(Long productId) throws SQLException;

    Boolean isProductAlreadyCompared(Long userId, Long productId);
}
