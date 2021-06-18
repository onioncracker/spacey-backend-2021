package com.heroku.spacey.services;

import com.heroku.spacey.dto.order.OrderDetailsDto;

import java.sql.SQLException;

public interface OrderDetailsService {
    OrderDetailsDto getOrderDetails(Long orderId) throws SQLException;
}
