package com.heroku.spacey.dao;

import com.heroku.spacey.dto.order.OrderDetailsDto;

import java.sql.SQLException;

public interface OrderDetailsDao {
    OrderDetailsDto getOrderDetails(Long orderId) throws SQLException;
}
