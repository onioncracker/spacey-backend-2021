package com.heroku.spacey.dao;

import com.heroku.spacey.dto.order.CreateOrderDto;

import java.sql.Timestamp;

public interface OrderDao {
    Long insert(CreateOrderDto order, Timestamp orderTime, Timestamp deliveryTime);

    void addProductToOrder(Long orderId, Long productId, int amount, float sum);
}
