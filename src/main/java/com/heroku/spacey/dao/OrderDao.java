package com.heroku.spacey.dao;

import com.heroku.spacey.entity.Order;
import com.heroku.spacey.dto.order.CourierOrdersDto;

import java.sql.Date;
import java.util.List;

public interface OrderDao {

    void insert(Order order);

    void addProductToOrder(Long orderId, Long productId, int amount, float sum);

    List<CourierOrdersDto> getCourierOrders(Long orderId, Date date);
}
