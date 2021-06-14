package com.heroku.spacey.services;

import com.heroku.spacey.entity.Cart;

public interface CartService {
    void addProductToCart(Long productId, int amount);
    void deleteProductFromCart(Long productId, int amount);
    Cart getCart(Long userId);
}
