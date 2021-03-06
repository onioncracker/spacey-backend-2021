package com.heroku.spacey.dao;

import com.heroku.spacey.dto.cart.ProductForCartDto;
import com.heroku.spacey.entity.Product;
import com.heroku.spacey.entity.ProductToCart;

import java.util.List;

public interface ProductToCartDao {

    void insert(Long cartId, Long productId, Long sizeId, int amount, double sum);
    ProductToCart get(Long cartId, Long productId, Long sizeId);
    void update(ProductToCart productToCart);
    void delete(ProductToCart productToCart);
    List<ProductToCart> getAllInCart(Long cartId);
    List<ProductForCartDto> getAllProducts(Long cartId);
    List<Product> getAllProductsByUserId(Long userId);
}
