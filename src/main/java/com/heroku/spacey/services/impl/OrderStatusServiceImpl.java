package com.heroku.spacey.services.impl;

import com.heroku.spacey.dao.OrderStatusDao;
import com.heroku.spacey.entity.OrderStatus;
import com.heroku.spacey.services.OrderStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderStatusServiceImpl implements OrderStatusService {

    private final OrderStatusDao orderStatusDao;


    @Override
    public List<OrderStatus> getOrderStatuses() {
        return orderStatusDao.getAll();
    }

    @Override
    public OrderStatus getOrderStatusById(Long orderStatusId) {
        return orderStatusDao.getById(orderStatusId);
    }

    @Override
    public OrderStatus getOrderStatusByName(String status) {
        return orderStatusDao.getByName(status);
    }

    @Override
    public void createOrderStatus(OrderStatus orderStatus) {
        orderStatusDao.insert(orderStatus);
    }

    @Override
    public int updateOrderStatus(OrderStatus orderStatus) {
        if (orderStatusDao.update(orderStatus) == 0) {
            throw new NotFoundException("Haven't found order status with such ID.");
        }

        return orderStatusDao.update(orderStatus);
    }

    @Override
    public int deleteOrderStatus(Long orderStatusId) {
        if (orderStatusDao.delete(orderStatusId) == 0) {
            throw new NotFoundException("Haven't found order status with such ID.");
        }

        return orderStatusDao.delete(orderStatusId);
    }
}
