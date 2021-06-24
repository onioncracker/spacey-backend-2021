package com.heroku.spacey.services.impl;

import com.heroku.spacey.entity.OrderStatus;
import com.heroku.spacey.entity.Size;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import com.heroku.spacey.dao.OrderDao;
import com.heroku.spacey.entity.Product;
import com.heroku.spacey.dao.ProductDao;
import com.heroku.spacey.dao.OrderStatusDao;
import com.heroku.spacey.entity.SizeToProduct;
import org.springframework.stereotype.Service;
import com.heroku.spacey.services.OrderService;
import com.heroku.spacey.dto.order.CreateOrderDto;
import com.heroku.spacey.utils.security.SecurityUtils;
import com.heroku.spacey.dto.order.ProductCreateOrderDto;
import com.heroku.spacey.error.ProductOutOfStockException;
import org.webjars.NotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.sql.Timestamp;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final ProductDao productDao;
    private final OrderDao orderDao;
    private final OrderStatusDao orderStatusDao;
    private final SecurityUtils securityUtils;


    @Override
    @Transactional
    public void createOrder(CreateOrderDto createOrderDto) throws IllegalArgumentException {
        setOrderComment(createOrderDto);
        setOrderStatus(createOrderDto);
        updateStock(createOrderDto);
        addProductsToOrder(createOrderDto);
    }

    private void setOrderComment(CreateOrderDto createOrderDto) {
        StringBuilder commentOptions = new StringBuilder(createOrderDto.getCommentOrder());
        if (createOrderDto.isDoNotDisturb()) {
            commentOptions.append("\nDo not disturb me, please.");
        }
        if (createOrderDto.isNoContact()) {
            commentOptions.append("\nI want this to be a 'no contact' delivery.");
        }
        createOrderDto.setCommentOrder(commentOptions.toString());
    }

    private void setOrderStatus(CreateOrderDto createOrderDto) {
        Long orderStatusId;
        try {
            orderStatusId = orderStatusDao.getByName("SUBMITTED").getOrderStatusId();
        } catch (NotFoundException e) {
            OrderStatus orderStatus = new OrderStatus();
            orderStatus.setStatus("SUBMITTED");
            orderStatusDao.insert(orderStatus);
            orderStatusId = orderStatusDao.getByName("SUBMITTED").getOrderStatusId();
        }
        createOrderDto.setOrderStatusId(orderStatusId);
    }

    /////////////////////////////////////
    // TODO: make another method with inner for
    //////////////////////////////////////
    private void updateStock(CreateOrderDto createOrderDto) {
        for (ProductCreateOrderDto orderProduct : createOrderDto.getProducts()) {
            Product productOnStock = productDao.get(orderProduct.getProductId());
            Set<Size> productSizesOnStock = productOnStock.getSizes();

            for (Size productSizeOnStock : productSizesOnStock) {
                if (productSizeOnStock.getId().equals(orderProduct.getSizeId())) {
                    Long quantity = productSizeOnStock.getQuantity();
                    productSizeOnStock.setQuantity(quantity - orderProduct.getAmount());
                    SizeToProduct sizeToProduct = new SizeToProduct(productSizeOnStock.getId(),
                                                                    productOnStock.getId(),
                                                                    productSizeOnStock.getQuantity());
                    checkAvailability(sizeToProduct);
                    productDao.updateSizeToProduct(sizeToProduct);
                }
            }
        }
    }

    private void checkAvailability(SizeToProduct sizeToProduct) {
        if (sizeToProduct.getQuantity() < 0) {
            throw new ProductOutOfStockException("There aren't enough products in stock.");
        }
    }

    private void addProductsToOrder(CreateOrderDto createOrderDto) {
        Timestamp orderTime = new Timestamp(System.currentTimeMillis());
        Long userId = securityUtils.getUserIdByToken();
        Long orderId = orderDao.insert(createOrderDto, orderTime, userId);
        orderDao.addUserToOrders(orderId, userId);
        for (ProductCreateOrderDto product : createOrderDto.getProducts()) {
            int amount = product.getAmount();
            float sum = product.getSum();
            orderDao.addProductToOrder(orderId, product.getProductId(), product.getSizeId(), amount, sum);
        }
    }
}
