package com.heroku.spacey.services;

import com.heroku.spacey.dto.order.AllOrderStatusDto;
import com.heroku.spacey.dto.order.OrderDetailsDto;
import com.heroku.spacey.dto.order.OrderStatusDto;

import java.sql.SQLException;
import java.util.List;

public interface OrderDetailsService {
    OrderDetailsDto getOrderDetails(Long orderId) throws SQLException;

    void updateOrderStatus(OrderStatusDto orderStatusDto);

    List<AllOrderStatusDto> getAllOrderStatus();
}
