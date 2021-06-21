package com.heroku.spacey.services.impl;

import com.heroku.spacey.entity.Size;
import lombok.RequiredArgsConstructor;
import com.heroku.spacey.dao.OrderDao;
import com.heroku.spacey.entity.Product;
import com.heroku.spacey.dao.ProductDao;
import com.heroku.spacey.dao.OrderStatusDao;
import com.heroku.spacey.entity.SizeToProduct;
import org.springframework.stereotype.Service;
import com.heroku.spacey.services.OrderService;
import com.heroku.spacey.dto.order.CreateOrderDto;
import com.heroku.spacey.dto.order.ProductCreateOrderDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final ProductDao productDao;
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
        Long userId = 1L;

        for (ProductCreateOrderDto product : createOrderDto.getProducts()) {
            Product productOnStock = productDao.get(product.getProductId());
            Set<Size> productSizesOnStock = productOnStock.getSizes();

            for (Size productSizeOnStock : productSizesOnStock) {
                if (productSizeOnStock.getId().equals(product.getSizeId())) {
                    Long quantity = productSizeOnStock.getQuantity();
                    productSizeOnStock.setQuantity(quantity - product.getAmount());
                    SizeToProduct sizeToProduct = new SizeToProduct(productSizeOnStock.getId(),
                                                                    productOnStock.getId(),
                                                                    productSizeOnStock.getQuantity());
                    if (sizeToProduct.getQuantity() < 0) {
                        throw new IllegalArgumentException("There aren't enough products in stock.");
                    }
                    productDao.updateSizeToProduct(sizeToProduct);
                }
            }
        }

        Long orderId = orderDao.insert(createOrderDto, orderTime, userId);
        orderDao.addUserToOrders(orderId, userId);
        for (ProductCreateOrderDto product : createOrderDto.getProducts()) {
            int amount = product.getAmount();
            float sum = product.getSum();
            orderDao.addProductToOrder(orderId, product.getProductId(), product.getSizeId(), amount, sum);
        }
    }
}
