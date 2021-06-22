package com.heroku.spacey.services;

import com.heroku.spacey.dto.product.AddProductDto;
import com.heroku.spacey.dto.product.ProductDto;
import com.heroku.spacey.dto.product.UpdateProductDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> getAllProducts();

    ProductDto getById(Long id);

    Long addProduct(AddProductDto addProductDto);

    void updateProduct(UpdateProductDto updateProductDto, Long id);

    void removeProduct(Long id);

    void cancelProduct(Long id);

    boolean checkAmount(Long productId, Long sizeId, double amount);
}
