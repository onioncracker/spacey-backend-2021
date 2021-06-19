package com.heroku.spacey.services.impl;

import com.heroku.spacey.dao.ProductCompareDao;
import com.heroku.spacey.dao.ProductDao;
import com.heroku.spacey.dto.product.ProductItemDto;
import com.heroku.spacey.services.ProductCompareService;
import com.heroku.spacey.utils.security.SecurityUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.sql.SQLException;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
@PropertySource("classpath:const.properties")
public class ProductCompareServiceImpl implements ProductCompareService {
    @Value("${max_compared_products}")
    private Integer maxCountComparingProducts;
    @Value("${min_compared_products}")
    private Integer minCountComparingProducts;

    private final ProductCompareDao productCompareDao;
    private final SecurityUtils securityUtils;
    private final ProductDao productDao;


    public void addProductToCompare(Long productId) throws SQLException {
        Long userId = securityUtils.getUserIdByToken();
        if (!productDao.isExist(productId)) {
            throw new IllegalArgumentException("This product not exist");
        }
        if (productCompareDao.getCountComparingProduct(userId) >= maxCountComparingProducts) {
            throw new IllegalArgumentException("More then 4 product to compare");
        }
        if (productCompareDao.isProductAlreadyCompared(userId, productId)) {
            throw new IllegalArgumentException("This product already compared");
        }
        productCompareDao.addToCompare(userId, productId);
        log.info("product add to compare");
    }

    public List<ProductItemDto> getAllComparingProduct() throws SQLException {
        Long userId = securityUtils.getUserIdByToken();
        if (productCompareDao.getCountComparingProduct(userId) < minCountComparingProducts) {
            throw new NotFoundException("Need more 1 product to compare");
        }
        return productCompareDao.getAllProduct(userId);
    }

    public void deleteProductCompare(Long productId) throws SQLException {
        Long userId = securityUtils.getUserIdByToken();
        if (!productDao.isExist(productId)) {
            throw new IllegalArgumentException("This product not exist");
        }
        if (!productCompareDao.isProductAlreadyCompared(userId, productId)) {
            throw new IllegalArgumentException("This product already deleted from comparing");
        }
        productCompareDao.deleteCompare(userId, productId);
        log.info("product deleted from comparing");
    }
}
