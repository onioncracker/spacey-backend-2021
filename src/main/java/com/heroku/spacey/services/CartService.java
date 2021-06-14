package com.heroku.spacey.services;

import com.heroku.spacey.dto.product.ProductForCartDto;
import com.heroku.spacey.entity.Cart;

import java.util.List;

public interface CartService {
    void addProductToCart(Long productId, int amount);
    void deleteProductFromCart(Long productId, int amount);
    Cart getCartForCurrentUser();
    void cleanCart();
    List<ProductForCartDto> getAllProductsInCart();
}
