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
import com.heroku.spacey.utils.OrderStatusChangerTask;
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
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

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
    public void createOrderForAuthorizedUser(CreateOrderDto order, boolean isAfterAuction)
            throws IllegalArgumentException,
            SQLException,
            NoSuchAlgorithmException {
        createOrder(order, isAfterAuction);

        addUserToOrders();
        cartService.cleanCart();
    }

    @Override
    @Transactional
    public void createOrderForAnonymousUser(CreateOrderDto order) throws IllegalArgumentException,
                                                                         SQLException,
                                                                         NoSuchAlgorithmException {
        createOrder(order, false);
    }

    private void createOrder(CreateOrderDto order, boolean isAfterAuction) throws NoSuchAlgorithmException,
                                                                                  SQLException {
        setOrderComment(order);
        setOrderStatus(order);

        if (!isAfterAuction) {
            updateStock(order);
        }

        Timestamp orderTime = new Timestamp(System.currentTimeMillis());
        Timestamp dateDelivery = new Timestamp(System.currentTimeMillis());
        order.setDateCreate(orderTime);
        order.setDateDelivery(dateDelivery);
        orderId = orderDao.insert(order);

        addProductsToOrder(order);
        Random rand = SecureRandom.getInstanceStrong();
        assignCourier(order, rand);
        scheduleOrderStatusChange(order);
    }

    private void setOrderComment(CreateOrderDto order) {
        StringBuilder commentOptions = new StringBuilder(order.getCommentOrder());
        if (order.isDoNotDisturb()) {
            commentOptions.append("\nDo not disturb me, please.");
        }
        if (order.isNoContact()) {
            commentOptions.append("\nI want this to be a 'no contact' delivery.");
        }
        order.setCommentOrder(commentOptions.toString());
    }

    private void setOrderStatus(CreateOrderDto order) {
        Long orderStatusId;
        try {
            orderStatusId = orderStatusDao.getByName("SUBMITTED").getOrderStatusId();
        } catch (NotFoundException e) {
            OrderStatus orderStatus = new OrderStatus();
            orderStatus.setStatus("SUBMITTED");
            orderStatusId = orderStatusDao.insert(orderStatus);
        }
        order.setOrderStatusId(orderStatusId);
    }

    private void updateStock(CreateOrderDto order) {
        for (ProductCreateOrderDto orderProduct : order.getProducts()) {
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

    private void addProductsToOrder(CreateOrderDto order) {
        for (ProductCreateOrderDto product : order.getProducts()) {
            int amount = product.getAmount();
            float sum = product.getSum();
            orderDao.addProductToOrder(orderId, product.getProductId(), product.getSizeId(), amount, sum);
        }
    }

    private void addUserToOrders() {
        Long userId = securityUtils.getUserIdByToken();
        orderDao.addUserToOrders(orderId, userId);
    }

    private void assignCourier(CreateOrderDto order, Random rand) throws SQLException {
        List<EmployeeDto> availableCouriers = employeeDao.getAvailableCouriers(order.getDateDelivery());

        if (availableCouriers.isEmpty()) {
            throw new NoAvailableCourierException("We couldn't found available courier "
                                                 + "for selected delivery timeslot.");
        }

        int selectedCourierIndex = rand.nextInt(availableCouriers.size());

        EmployeeDto selectedCourier = availableCouriers.get(selectedCourierIndex);
        orderDao.addUserToOrders(orderId, selectedCourier.getUserId());
    }

    private void scheduleOrderStatusChange(CreateOrderDto order) {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        long timeToStatusChange = order.getDateDelivery().getTime() - 3_600_000 - System.currentTimeMillis();
        if (timeToStatusChange <= 0) {
            timeToStatusChange = 1000;
        }
        service.schedule(new OrderStatusChangerTask(order, orderStatusDao), timeToStatusChange, TimeUnit.MILLISECONDS);
    }
}
