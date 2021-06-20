package com.heroku.spacey.services;

import com.heroku.spacey.dto.order.OrderDetailsDto;
import com.heroku.spacey.dto.order.OrderStatusDto;

import java.sql.SQLException;

public interface OrderDetailsService {
    OrderDetailsDto getOrderDetails(Long orderId) throws SQLException;

    void updateOrderStatus(OrderStatusDto orderStatusDto);
}
