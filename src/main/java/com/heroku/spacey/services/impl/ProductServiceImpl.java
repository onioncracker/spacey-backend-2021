package com.heroku.spacey.services.impl;

import com.heroku.spacey.dao.ProductDao;
import com.heroku.spacey.dao.ProductDetailDao;
import com.heroku.spacey.dto.product.AddProductDto;
import com.heroku.spacey.entity.Product;
import com.heroku.spacey.services.ProductService;
import com.heroku.spacey.dto.product.ProductDto;
import com.heroku.spacey.dto.product.UpdateProductDto;
import com.heroku.spacey.utils.ProductConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

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
    public ProductDto getById(Long id) {
        Product product = productDao.get(id);
        if (product == null) {
            throw new NotFoundException("Product not found");
        }
        return productConvertor.adapt(product);
    }

    @Override
    @Transactional
    public void addProduct(AddProductDto addProductDto) {
        Product product = productConvertor.adapt(addProductDto);
        Long categoryId = addProductDto.getCategory().getId();
        product.setCategoryId(categoryId);

        Long productId = productDao.insert(product);
        product.getProductDetail().setProductId(productId);
        productDetailDao.insert(product.getProductDetail());

        for (var i = 0; i < addProductDto.getMaterials().size(); i++) {
            var materialId = addProductDto.getMaterials().get(i).getId();
            productDao.addMaterialToProduct(materialId, productId);
        }
    }

    @Override
    @Transactional
    public void updateProduct(UpdateProductDto updateProductDto, Long id) {
        Product product = productConvertor.adapt(updateProductDto);
        product.setId(id);
        productDao.update(product);
        productDetailDao.update(product.getProductDetail());
    }

    @Override
    @Transactional
    public void removeProduct(Long id) {
        boolean isFound = productDao.isExist(id);
        if (!isFound) {
            new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        productDao.deactivate(id);
    }

    @Override
    @Transactional
    public void cancelProduct(Long id) {
        boolean isFound = productDao.isExist(id);
        if (!isFound) {
            new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        productDao.delete(id);
    }
}
