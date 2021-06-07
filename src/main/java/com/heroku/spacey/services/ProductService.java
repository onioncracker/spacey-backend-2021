package com.heroku.spacey.services;

import com.heroku.spacey.dto.product.AddProductDto;
import com.heroku.spacey.dto.product.ProductDto;
import com.heroku.spacey.dto.product.UpdateProductDto;

public interface ProductService {
    ProductDto getById(Long id);

    void addProduct(AddProductDto addProductDto);

    void updateProduct(UpdateProductDto updateProductDto, Long id);

    void removeProduct(Long id);

    void cancelProduct(Long id);
}
