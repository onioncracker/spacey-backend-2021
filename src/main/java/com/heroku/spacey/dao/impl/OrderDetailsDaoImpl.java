package com.heroku.spacey.dao.impl;

import com.heroku.spacey.dao.OrderDetailsDao;
import com.heroku.spacey.dto.order.OrderDetailsDto;
import com.heroku.spacey.dto.order.OrderStatusDto;
import com.heroku.spacey.dto.product.ProductOrderDto;
import com.heroku.spacey.mapper.order.OrderDetailsMapper;
import com.heroku.spacey.mapper.order.ProductsInOrderMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
@PropertySource("classpath:sql/order_details_queries.properties")
public class OrderDetailsDaoImpl implements OrderDetailsDao {

    private final JdbcTemplate jdbcTemplate;
    private final ProductsInOrderMapper productsInOrderMapper;

    @Value("${select_details_by_order_id}")
    private String sqlGetOrderDetails;

    @Value("${products_in_order}")
    private String sqlProductInOrder;

    @Value("${change_order_status}")
    private String sqlChangeOrderStatus;

    @Value("${set_order_status}")
    private String sqlSetOrderStatus;

    @Override
    public OrderDetailsDto getOrderDetails(Long orderId) {
        OrderDetailsMapper mapper = new OrderDetailsMapper(getAllProductInOrder(orderId));
        return jdbcTemplate.query(sqlGetOrderDetails, mapper, orderId);
    }

    public List<ProductOrderDto> getAllProductInOrder(Long orderId) {
        return jdbcTemplate.query(sqlProductInOrder, productsInOrderMapper, orderId);
    }

    @Override
    public void updateOrderStatus(OrderStatusDto orderStatusDto) {
        Objects.requireNonNull(jdbcTemplate).update(
                sqlChangeOrderStatus,
                orderStatusDto.getOrderStatusId(),
                orderStatusDto.getOrderId()
        );
    }

    @Override
    public void setDeliveredStatus(Long orderId) {
        String deliveredStatus = "DELIVERED";
        jdbcTemplate.update(sqlSetOrderStatus, deliveredStatus, orderId);
    }

    @Override
    public void setFailStatus(Long orderId) {
        String failStatus = "FAIL";
        jdbcTemplate.update(sqlSetOrderStatus, failStatus, orderId);
    }
}
