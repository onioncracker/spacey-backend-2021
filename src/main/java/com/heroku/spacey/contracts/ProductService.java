package com.heroku.spacey.contracts;

import com.heroku.spacey.dto.product.AddProductDto;
import com.heroku.spacey.dto.product.UpdateProductDto;

public interface ProductService {
    void addProduct(AddProductDto addProductDto);

    void updateProduct(UpdateProductDto updateProductDto);
}
