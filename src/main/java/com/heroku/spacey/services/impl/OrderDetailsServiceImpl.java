package com.heroku.spacey.services.impl;

import com.heroku.spacey.dao.OrderDetailsDao;
import com.heroku.spacey.dto.order.AllOrderStatusDto;
import com.heroku.spacey.dto.order.OrderDetailsDto;
import com.heroku.spacey.dto.order.OrderStatusDto;
import com.heroku.spacey.entity.OrderStatus;
import com.heroku.spacey.services.OrderDetailsService;
import com.heroku.spacey.utils.convertors.CommonConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {

    private final OrderDetailsDao orderDetailsDao;
    private final CommonConvertor commonConvertor;

    public OrderDetailsServiceImpl(@Autowired OrderDetailsDao orderDetailsDao,
                                   @Autowired CommonConvertor commonConvertor) {
        this.orderDetailsDao = orderDetailsDao;
        this.commonConvertor = commonConvertor;
    }

    @Override
    public OrderDetailsDto getOrderDetails(Long orderId) throws SQLException {
        return orderDetailsDao.getOrderDetails(orderId);
    }

    @Override
    @Transactional
    public void updateOrderStatus(OrderStatusDto orderStatusDto) {
        orderDetailsDao.updateOrderStatus(orderStatusDto);
    }


    public List<AllOrderStatusDto> getAllOrderStatus() {
        List<OrderStatus> orderStatuses = orderDetailsDao.getAllOrderStatus();
        return commonConvertor.mapList(orderStatuses, AllOrderStatusDto.class);
    }
}
