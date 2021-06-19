package com.heroku.spacey.services;

import com.heroku.spacey.entity.OrderStatus;

import java.util.List;

public interface OrderStatusService {
    List<OrderStatus> getOrderStatuses();
    OrderStatus getOrderStatusById(Long orderStatusId);
    OrderStatus getOrderStatusByName(String status);
    void createOrderStatus(OrderStatus orderStatus);
    int updateOrderStatus(OrderStatus orderStatus);
    int deleteOrderStatus(Long orderStatusId);
}
