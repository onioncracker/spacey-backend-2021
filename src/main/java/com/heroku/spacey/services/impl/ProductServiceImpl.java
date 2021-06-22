package com.heroku.spacey.services.impl;

import com.heroku.spacey.dao.ProductDao;
import com.heroku.spacey.dto.product.AddProductDto;
import com.heroku.spacey.dto.size.SizeDto;
import com.heroku.spacey.entity.Product;
import com.heroku.spacey.services.ProductService;
import com.heroku.spacey.dto.product.ProductDto;
import com.heroku.spacey.dto.product.UpdateProductDto;
import com.heroku.spacey.utils.convertors.CommonConvertor;
import com.heroku.spacey.utils.convertors.ProductConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductDao productDao;
    private final CommonConvertor commonConvertor;
    private final ProductConvertor productConvertor;

    public ProductServiceImpl(@Autowired ProductDao productDao,
                              @Autowired CommonConvertor commonConvertor,
                              @Autowired ProductConvertor productConvertor) {
        this.productDao = productDao;
        this.commonConvertor = commonConvertor;
        this.productConvertor = productConvertor;
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productDao.getAllProducts();
        return commonConvertor.mapList(products, ProductDto.class);
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
    public Long addProduct(AddProductDto addProductDto) {
        Product product = productConvertor.adapt(addProductDto);
        Long categoryId = addProductDto.getCategory().getId();
        Long colorId = addProductDto.getColor().getId();
        product.setCategoryId(categoryId);
        product.setColorId(colorId);

        Long productId = productDao.insert(product);

        for (int i = 0; i < addProductDto.getMaterials().size(); i++) {
            Long materialId = addProductDto.getMaterials().get(i).getId();
            productDao.addMaterialToProduct(materialId, productId);
        }

        for (int i = 0; i < addProductDto.getSizes().size(); i++) {
            SizeDto size = addProductDto.getSizes().get(i);
            productDao.addSizeToProduct(size.getId(), productId, size.getQuantity());
        }
        return productId;
    }

    @Override
    @Transactional
    public void updateProduct(UpdateProductDto updateProductDto, Long id) {
        Product product = productConvertor.adapt(updateProductDto);
        product.setId(id);
        productDao.update(product);
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

    @Override
    @Transactional
    public boolean checkAmount(Long productId, Long sizeId, double amount) {
        Product product = productDao.get(productId);
        double maxAmount = productDao.getAmount(sizeId, productId);
        return product.getIsAvailable() && amount < maxAmount;
    }
}
