package com.heroku.spacey.mapper;

import com.heroku.spacey.entity.OrderStatus;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class OrderStatusMapper implements RowMapper<OrderStatus> {
    @Override
    public OrderStatus mapRow(ResultSet resultSet, int i) throws SQLException {
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderStatusId(resultSet.getLong("orderstatusid"));
        orderStatus.setStatus(resultSet.getString("status"));
        return orderStatus;
    }
}
