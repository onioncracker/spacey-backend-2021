package com.heroku.spacey.dao;

import com.heroku.spacey.entity.Cart;

public interface CartDao {
    Long getCartIdByUserId(Long userId);
    Long createCart(Long userId);
    Cart getCart(Long cartId);
    void updatePrice(Long cartId, double sum);
}
