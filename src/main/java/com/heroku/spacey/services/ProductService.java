package com.heroku.spacey.services;

import com.heroku.spacey.dao.category.CategoryDaoImpl;
import com.heroku.spacey.dao.product.ProductDaoImpl;
import com.heroku.spacey.dto.product.AddProductDto;
import com.heroku.spacey.dto.product.ProductDetailDto;
import com.heroku.spacey.entity.Category;
import com.heroku.spacey.entity.Material;
import com.heroku.spacey.entity.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    final ProductDaoImpl productDao;
    final CategoryDaoImpl categoryDao;

    public ProductService(ProductDaoImpl productDao, CategoryDaoImpl categoryDao) {
        this.productDao = productDao;
        this.categoryDao = categoryDao;
    }

    public ProductDetailDto getProductDetailById(int id) {
        var product = productDao.getById(id);
        var productDetailDto = new ProductDetailDto();
        productDetailDto.setName(product.getName());
        productDetailDto.setProductId(product.getId());
        productDetailDto.setDiscount(product.getDiscount());
        return productDetailDto;
    }

    public void addProduct(AddProductDto addProductDto) {
        Product product = new Product();
        Category category = new Category();
        category.setName(addProductDto.getCategoryName());
        int categoryId = categoryDao.insert(category);
        product.setName(addProductDto.getName());
        product.setCategoryId(categoryId);
        productDao.insert(product);
    }
}
