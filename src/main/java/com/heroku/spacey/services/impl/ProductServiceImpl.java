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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.List;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductDao productDao;
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
        Long categoryId = updateProductDto.getCategory().getId();
        Long colorId = updateProductDto.getColor().getId();
        product.setCategoryId(categoryId);
        product.setColorId(colorId);

        productDao.update(product);

        productDao.deleteMaterialToProduct(id);
        productDao.deleteSizeToProduct(id);

        for (int i = 0; i < updateProductDto.getMaterials().size(); i++) {
            Long materialId = updateProductDto.getMaterials().get(i).getId();
            productDao.addMaterialToProduct(materialId, id);
        }

        for (int i = 0; i < updateProductDto.getSizes().size(); i++) {
            SizeDto size = updateProductDto.getSizes().get(i);
            productDao.addSizeToProduct(size.getId(), id, size.getQuantity());
        }
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
    public boolean checkAmount(Long productId, Long sizeId, int amount) {
        log.info("in product service: " + productId + " " + sizeId + " " + amount);
        boolean available = productDao.isAvailable(productId);
        int maxAmount = productDao.getAmount(sizeId, productId);
        return available && (amount < maxAmount);
    }
}
