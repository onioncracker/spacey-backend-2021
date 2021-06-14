package com.heroku.spacey.dao;

import com.heroku.spacey.entity.Order;
import com.heroku.spacey.entity.Product;

public interface OrderDao {

    void insert(Order order);

    void addProductToOrder(Long orderId, Long productId, int amount, float sum);
}
