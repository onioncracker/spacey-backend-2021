package com.heroku.spacey.services.impl;

import com.heroku.spacey.dao.OrderDetailsDao;
import com.heroku.spacey.dto.order.OrderDetailsDto;
import com.heroku.spacey.services.OrderDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class OrderDetailsServiceImpl implements OrderDetailsService {

    private final OrderDetailsDao orderDetailsDao;

    @Override
    public OrderDetailsDto getOrderDetails(Long orderId) throws SQLException {
        return orderDetailsDao.getOrderDetails(orderId);
    }
}
