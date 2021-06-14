package com.heroku.spacey.dao;

public interface ProductToCartDao {

    void insert(Long cartId, Long userId, int amount, double sum);
}
