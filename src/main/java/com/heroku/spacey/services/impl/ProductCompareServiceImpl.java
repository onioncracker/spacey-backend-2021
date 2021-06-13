package com.heroku.spacey.services.impl;

import com.heroku.spacey.dao.ProductCompareDao;
import com.heroku.spacey.dto.product.ProductCompareDto;
import com.heroku.spacey.dto.product.ProductItemDto;
import com.heroku.spacey.services.ProductCompareService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.sql.SQLException;
import java.util.List;


@Service
@RequiredArgsConstructor
@PropertySource("classpath:const.properties")
public class ProductCompareServiceImpl implements ProductCompareService {
    @Value("${max_compared_products}")
    private int maxCountComparingProducts;
    @Value("${min_compared_products}")
    private int minCountComparingProducts;

    private final ProductCompareDao productCompareDao;


    public void addProductToCompare(ProductCompareDto compareDto) throws SQLException {
        if (productCompareDao.getCountComparingProduct(compareDto.getUserId()) >= maxCountComparingProducts) {
            throw new IllegalArgumentException("More then 4 product to compare");
        }
        productCompareDao.addToCompare(compareDto);
    }

    public List<ProductItemDto> getAllComparingProduct(Long userId) throws SQLException {
        if (productCompareDao.getCountComparingProduct(userId) < minCountComparingProducts) {
            throw new NotFoundException("Need more 1 product to compare");
        }
        return productCompareDao.getAllProduct(userId);
    }

    public void deleteProductCompare(ProductCompareDto compareDto) throws SQLException {
        productCompareDao.deleteCompare(compareDto);
    }
}
