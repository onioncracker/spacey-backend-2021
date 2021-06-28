package com.heroku.spacey.utils;

import com.heroku.spacey.dao.OrderStatusDao;
import com.heroku.spacey.dto.order.CreateOrderDto;
import com.heroku.spacey.entity.OrderStatus;
import lombok.Data;
import lombok.AllArgsConstructor;
import org.webjars.NotFoundException;

@Data
@AllArgsConstructor
public class OrderStatusChangerTask implements Runnable {
    private CreateOrderDto createOrderDto;
    private OrderStatusDao orderStatusDao;

    @Override
    public void run() {
        Long orderStatusId;

        try {
            orderStatusId = orderStatusDao.getByName("IN DELIVERY").getOrderStatusId();
        } catch (NotFoundException e) {
            OrderStatus orderStatus = new OrderStatus();
            orderStatus.setStatus("IN DELIVERY");
            orderStatusId = orderStatusDao.insert(orderStatus);
        }

        createOrderDto.setOrderStatusId(orderStatusId);
    }
}
