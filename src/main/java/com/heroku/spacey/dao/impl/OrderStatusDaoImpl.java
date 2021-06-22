package com.heroku.spacey.dao.impl;

import lombok.RequiredArgsConstructor;
import com.heroku.spacey.dao.OrderStatusDao;
import com.heroku.spacey.entity.OrderStatus;
import com.heroku.spacey.mapper.OrderStatusMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor

@PropertySource("classpath:sql/order_status_queries.properties")
public class OrderStatusDaoImpl implements OrderStatusDao {
    private final OrderStatusMapper mapper = new OrderStatusMapper();
    private final JdbcTemplate jdbcTemplate;

    @Value("${select_order_statuses}")
    private String sqlSelectOrderStatuses;
    @Value("${select_order_status_by_id}")
    private String sqlSelectOrderStatusById;
    @Value("${select_order_status_by_name}")
    private String sqlSelectOrderStatusByName;
    @Value("${insert_order_status}")
    private String sqlInsertOrderStatus;
    @Value("${update_order_status}")
    private String sqlUpdateOrderStatus;
    @Value("${delete_order_status}")
    private String sqlDeleteOrderStatus;


    @Override
    public List<OrderStatus> getAll() {
        List<OrderStatus> orderStatuses = Objects.requireNonNull(jdbcTemplate).query(sqlSelectOrderStatuses, mapper);

        if (orderStatuses.isEmpty()) {
            return new ArrayList<>();
        }

        return orderStatuses;
    }

    @Override
    public OrderStatus getById(Long orderStatusId) {
        List<OrderStatus> orderStatuses = Objects.requireNonNull(jdbcTemplate).query(sqlSelectOrderStatusById, mapper, orderStatusId);

        if (orderStatuses.isEmpty()) {
            throw new NotFoundException("Haven't found order status with such ID.");
        }

        return orderStatuses.get(0);
    }

    @Override
    public OrderStatus getByName(String status) {
        List<OrderStatus> orderStatuses = Objects.requireNonNull(jdbcTemplate).query(sqlSelectOrderStatusByName, mapper, status);

        if (orderStatuses.isEmpty()) {
            throw new NotFoundException("Haven't found order status with such name.");
        }

        return orderStatuses.get(0);
    }

    @Override
    public void insert(OrderStatus orderStatus) {
        String status = orderStatus.getStatus();

        jdbcTemplate.update(sqlInsertOrderStatus, status);
    }

    @Override
    public int update(OrderStatus orderStatus) {
        Long orderStatusId = orderStatus.getOrderStatusId();
        String status = orderStatus.getStatus();

        return jdbcTemplate.update(sqlUpdateOrderStatus, status, orderStatusId);
    }

    @Override
    public int delete(Long orderStatusId) {
        return jdbcTemplate.update(sqlDeleteOrderStatus, orderStatusId);
    }
}
