package com.heroku.spacey.services.impl;

import com.heroku.spacey.dao.OrderDao;
import com.heroku.spacey.dao.OrderStatusDao;
import com.heroku.spacey.dto.order.CreateOrderDto;
import com.heroku.spacey.dto.order.ProductCreateOrderDto;
import com.heroku.spacey.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderDao orderDao;
    private final OrderStatusDao orderStatusDao;


    @Transactional
    @Override
    public void createOrder(CreateOrderDto createOrderDto) throws IllegalArgumentException {
        StringBuilder commentOptions = new StringBuilder(createOrderDto.getCommentOrder());

        if (createOrderDto.isDoNotDisturb()) {
            commentOptions.append("\nDo not disturb me, please.");
        }
        if (createOrderDto.isNoContact()) {
            commentOptions.append("\nI want this to be a 'no contact' delivery.");
        }

        createOrderDto.setCommentOrder(commentOptions.toString());
        createOrderDto.setOrderStatusId(orderStatusDao.getByName("SUBMITTED").getOrderStatusId());

        Timestamp orderTime = new Timestamp(System.currentTimeMillis());
        //
        //////////////      TODO: change when input DTO is known
        //
        Timestamp deliveryTime = new Timestamp(System.currentTimeMillis());

        Long orderId = orderDao.insert(createOrderDto, orderTime, deliveryTime);

        for (ProductCreateOrderDto product: createOrderDto.getProducts()) {
            int amount = product.getAmount();
            float sum = product.getSum();
            orderDao.addProductToOrder(orderId, product.getProductId(), amount, sum);
        }
    }
}
