package com.heroku.spacey.contracts;

import com.heroku.spacey.dto.product.AddProductDto;
import com.heroku.spacey.dto.product.ProductDto;
import com.heroku.spacey.dto.product.UpdateProductDto;

public interface ProductService {
    ProductDto getById(int id);

    void addProduct(AddProductDto addProductDto);

    void updateProduct(UpdateProductDto updateProductDto);

    void removeProduct(int id);

    void cancelProduct(int id);
}
