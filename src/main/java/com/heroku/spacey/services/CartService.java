package com.heroku.spacey.services;

import com.heroku.spacey.dto.cart.EditCartDto;
import com.heroku.spacey.dto.cart.ProductForCartDto;
import com.heroku.spacey.entity.Cart;

import java.util.List;

public interface CartService {
    void addProductToCart(Long productId, Long sizeId, int amount);
    void deleteProductFromCart(Long productId, Long sizeId, int amount);
    Cart getCartForCurrentUser();
    void cleanCart();
    List<ProductForCartDto> getAllProductsInCart();
    List<ProductForCartDto> getUnauthorizedCart(List<EditCartDto> unauthorizedCart);
}
