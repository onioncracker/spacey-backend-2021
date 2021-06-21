package com.heroku.spacey.dao;

import com.heroku.spacey.dto.order.CreateOrderDto;

import java.sql.Timestamp;

public interface OrderDao {
    Long insert(CreateOrderDto order, Timestamp dateCreate, Long userId);

    void addProductToOrder(Long orderId, Long productId, Long sizeId, int amount, float sum);

    void addUserToOrders(Long orderId, Long userId);
}
