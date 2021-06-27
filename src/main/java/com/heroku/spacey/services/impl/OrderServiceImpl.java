package com.heroku.spacey.services.impl;

import com.heroku.spacey.dao.OrderDao;
import com.heroku.spacey.dao.ProductDao;
import com.heroku.spacey.dao.EmployeeDao;
import com.heroku.spacey.dao.OrderStatusDao;
import com.heroku.spacey.entity.Size;
import com.heroku.spacey.entity.Product;
import com.heroku.spacey.entity.OrderStatus;
import com.heroku.spacey.entity.SizeToProduct;
import com.heroku.spacey.dto.employee.EmployeeDto;
import com.heroku.spacey.dto.order.CreateOrderDto;
import com.heroku.spacey.dto.product.ProductCreateOrderDto;
import com.heroku.spacey.error.NoAvailableCourierException;
import com.heroku.spacey.services.CartService;
import com.heroku.spacey.services.OrderService;
import com.heroku.spacey.utils.security.SecurityUtils;
import com.heroku.spacey.error.ProductOutOfStockException;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.webjars.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.sql.SQLException;
import java.util.Set;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;
    private final OrderStatusDao orderStatusDao;
    private final ProductDao productDao;
    private final EmployeeDao employeeDao;
    private final CartService cartService;
    private final SecurityUtils securityUtils;

    private Long orderId;


    @Override
    @Transactional
    public void createOrder(CreateOrderDto createOrderDto) throws IllegalArgumentException,
                                                                  SQLException,
                                                                  NoSuchAlgorithmException {
        setOrderComment(createOrderDto);
        setOrderStatus(createOrderDto);
        updateStock(createOrderDto);

        Timestamp orderTime = new Timestamp(System.currentTimeMillis());
        createOrderDto.setDateCreate(orderTime);
        orderId = orderDao.insert(createOrderDto);

        addProductsToOrder(createOrderDto);
        Random rand = SecureRandom.getInstanceStrong();
        assignCourier(createOrderDto, rand);
        // TODO: just for registered user
        addUserToOrders();
        cartService.cleanCart();
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

    private void updateStock(CreateOrderDto createOrderDto) {
        for (ProductCreateOrderDto orderProduct : createOrderDto.getProducts()) {
            Product productOnStock = productDao.get(orderProduct.getProductId());
            Set<Size> productSizesOnStock = productOnStock.getSizes();

            for (Size productSizeOnStock : productSizesOnStock) {
                if (productSizeOnStock.getId().equals(orderProduct.getSizeId())) {
                    subtractProductsFromStock(productSizeOnStock, orderProduct, productOnStock);
                }
            }
        }
    }

    private void subtractProductsFromStock(Size productSizeOnStock,
                                           ProductCreateOrderDto orderProduct,
                                           Product productOnStock) {
        Long quantity = productSizeOnStock.getQuantity();
        productSizeOnStock.setQuantity(quantity - orderProduct.getAmount());
        SizeToProduct sizeToProduct = new SizeToProduct(productSizeOnStock.getId(),
                productOnStock.getId(),
                productSizeOnStock.getQuantity());
        checkAvailability(sizeToProduct);
        productDao.updateProductQuantity(sizeToProduct);
    }

    private void checkAvailability(SizeToProduct sizeToProduct) {
        if (sizeToProduct.getQuantity() < 0) {
            throw new ProductOutOfStockException("There aren't enough products in stock.");
        }
    }

    private void addProductsToOrder(CreateOrderDto createOrderDto) {
        for (ProductCreateOrderDto product : createOrderDto.getProducts()) {
            int amount = product.getAmount();
            float sum = product.getSum();
            orderDao.addProductToOrder(orderId, product.getProductId(), product.getSizeId(), amount, sum);
        }
    }

    private void addUserToOrders() {
        Long userId = securityUtils.getUserIdByToken();
        orderDao.addUserToOrders(orderId, userId);
    }

    private void assignCourier(CreateOrderDto createOrderDto, Random rand) throws SQLException {
        List<EmployeeDto> availableCouriers = employeeDao.getAvailableCouriers(createOrderDto.getDateDelivery());

        if (availableCouriers.isEmpty()) {
            throw new NoAvailableCourierException("We couldn't found available courier "
                                                 + "for selected delivery timeslot.");
        }

        int selectedCourierIndex = rand.nextInt(availableCouriers.size());

        EmployeeDto selectedCourier = availableCouriers.get(selectedCourierIndex);
        orderDao.addUserToOrders(orderId, selectedCourier.getUserId());
    }
}
