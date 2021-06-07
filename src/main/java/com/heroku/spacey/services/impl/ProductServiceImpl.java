package com.heroku.spacey.services.impl;

import com.heroku.spacey.dao.ProductDao;
import com.heroku.spacey.dao.ProductDetailDao;
import com.heroku.spacey.dto.product.AddProductDto;
import com.heroku.spacey.services.ProductService;
import com.heroku.spacey.dto.product.ProductDto;
import com.heroku.spacey.dto.product.UpdateProductDto;
import com.heroku.spacey.utils.convertors.ProductConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductDao productDao;
    private ProductDetailDao productDetailDao;
    private final ProductConvertor productConvertor;

    public ProductServiceImpl(@Autowired ProductDao productDao,
                              @Autowired ProductConvertor productConvertor,
                              @Autowired ProductDetailDao productDetailDao) {
        this.productDao = productDao;
        this.productConvertor = productConvertor;
        this.productDetailDao = productDetailDao;
    }

    @Override
    @Transactional
    public ProductDto getById(int id) {
        var product = productDao.get(id);
        if (product == null) {
            return null;
        }
        return productConvertor.adapt(product);
    }

    @Override
    @Transactional
    public void addProduct(AddProductDto addProductDto) {
        var product = productConvertor.adapt(addProductDto);
        var categoryId = addProductDto.getCategory().getId();
        product.setCategoryId(categoryId);

        var productId = productDao.insert(product);
        product.getProductDetail().setProductId(productId);
        productDetailDao.insert(product.getProductDetail());

        for (var i = 0; i < addProductDto.getMaterials().size(); i++) {
            var materialId = addProductDto.getMaterials().get(i).getId();
            productDao.addMaterialToProduct(materialId, productId);
        }
    }

    @Override
    @Transactional
    public void updateProduct(UpdateProductDto updateProductDto, int id) {
        var product = productConvertor.adapt(updateProductDto);
        product.setId(id);
        productDao.update(product);
        productDetailDao.update(product.getProductDetail());
    }

    @Override
    @Transactional
    public void removeProduct(int id) {
        var isFound = productDao.isExist(id);
        if (!isFound) {
            new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        productDao.deactivate(id);
    }

    @Override
    @Transactional
    public void cancelProduct(int id) {
        var isFound = productDao.isExist(id);
        if (!isFound) {
            new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        productDao.delete(id);
    }
}
