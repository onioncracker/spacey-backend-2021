package com.heroku.spacey.services.impl;


import com.heroku.spacey.dao.ProductCompareDao;
import com.heroku.spacey.dto.product.ProductCompareDto;
import com.heroku.spacey.dto.product.ProductItemDto;
import com.heroku.spacey.services.ProductCompareService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductCompareServiceImpl implements ProductCompareService {
    private static final int MAX_COUNT_COMPARING_PRODUCTS = 10;

    private final ProductCompareDao productCompareDao;


    public void addProductToCompare(ProductCompareDto compareDto) throws SQLException {
        if (productCompareDao.getCountComparingProduct(compareDto.getUserId()) >= MAX_COUNT_COMPARING_PRODUCTS) {
            throw new IllegalArgumentException("More then 10 product to compare");
        }
        productCompareDao.addToCompare(compareDto);
    }

    public List<ProductItemDto> getAllComparingProduct(int userId) throws SQLException {
        return productCompareDao.getAllProduct(userId);
    }

    public void deleteProductCompare(ProductCompareDto compareDto) throws SQLException {
        productCompareDao.deleteCompare(compareDto);
    }
}
