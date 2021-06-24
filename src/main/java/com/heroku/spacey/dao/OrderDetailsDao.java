package com.heroku.spacey.dao;

import com.heroku.spacey.dto.order.OrderDetailsDto;
import com.heroku.spacey.dto.order.OrderStatusDto;
import com.heroku.spacey.entity.OrderStatus;

import java.sql.SQLException;
import java.util.List;

public interface OrderDetailsDao {
    OrderDetailsDto getOrderDetails(Long orderId) throws SQLException;

    void updateOrderStatus(OrderStatusDto orderStatusDto);

    List<OrderStatus> getAllOrderStatus();
}
