package com.heroku.spacey.services;

import com.heroku.spacey.dto.ProductCompareDto;
import com.heroku.spacey.dto.ProductDetailDto;
import com.heroku.spacey.repositories.ProductCompareDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductCompareService {
    private final int MaxCountComparingProducts = 10;

    private final ProductCompareDao productCompareDao;


    public void addProductToCompare(ProductCompareDto compareDto) throws SQLException {
        if (productCompareDao.getCountComparingProduct(compareDto.getUserId()) >= MaxCountComparingProducts) {
            throw new IllegalArgumentException("More then 10 product to compare");
        }
        productCompareDao.addToCompare(compareDto);
    }

    public List<ProductDetailDto> getAllComparingProduct(int userId) throws SQLException {
        return productCompareDao.getAllProduct(userId);
    }

    public void deleteProductCompare(ProductCompareDto compareDto) throws SQLException {
        productCompareDao.deleteCompare(compareDto);
    }
}
