package com.heroku.spacey.dao;

import com.heroku.spacey.entity.OrderStatus;

import java.util.List;

public interface OrderStatusDao {
    List<OrderStatus> getAll();
    OrderStatus getById(Long orderStatusId);
    OrderStatus getByName(String status);
    Long insert(OrderStatus orderStatus);
    int update(OrderStatus orderStatus);
    int delete(Long orderStatusId);
}
