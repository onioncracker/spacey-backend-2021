package com.heroku.spacey.services;

import com.heroku.spacey.dao.CategoryDao;
import com.heroku.spacey.dao.MaterialDao;
import com.heroku.spacey.dao.ProductDao;
import com.heroku.spacey.dao.ProductDetailDao;
import com.heroku.spacey.dto.product.AddProductDto;
import com.heroku.spacey.services.ProductService;
import com.heroku.spacey.dto.product.ProductDto;
import com.heroku.spacey.dto.product.UpdateProductDto;
import com.heroku.spacey.mapper.ProductConvertor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductDao productDao;
    private CategoryDao categoryDao;
    private MaterialDao materialDao;
    private ProductDetailDao productDetailDao;
    private final ProductConvertor productConvertor;

    public ProductServiceImpl(@Autowired ProductDao productDao,
                              @Autowired CategoryDao categoryDao,
                              @Autowired MaterialDao materialDao,
                              @Autowired ProductConvertor productConvertor,
                              @Autowired ProductDetailDao productDetailDao) {
        this.productDao = productDao;
        this.categoryDao = categoryDao;
        this.materialDao = materialDao;
        this.productConvertor = productConvertor;
        this.productDetailDao = productDetailDao;
    }

    @Override
    public ProductDto getById(int id) {
        var product = productDao.get(id);
        if (product == null) {
            return null;
        }
        return productConvertor.adapt(product);
    }

    @Transactional
    @Override
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
    public void updateProduct(UpdateProductDto updateProductDto, int id) {
        var product = productConvertor.adapt(updateProductDto);
        product.setId(id);
        productDao.update(product);
        productDetailDao.update(product.getProductDetail());
    }

    @Override
    public void removeProduct(int id) {
        var isFound = productDao.isExist(id);
        if (!isFound) {
            new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        productDao.deactivate(id);
    }

    @Override
    public void cancelProduct(int id) {
        var isFound = productDao.isExist(id);
        if (!isFound) {
            new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        productDao.delete(id);
    }
}
