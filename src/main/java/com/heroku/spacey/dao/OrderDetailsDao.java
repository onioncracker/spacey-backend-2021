package com.heroku.spacey.dao;

import com.heroku.spacey.dto.order.OrderDetailsDto;
import com.heroku.spacey.dto.order.OrderStatusDto;

import java.sql.SQLException;

public interface OrderDetailsDao {
    OrderDetailsDto getOrderDetails(Long orderId) throws SQLException;

    void updateOrderStatus (OrderStatusDto orderStatusDto);
}
