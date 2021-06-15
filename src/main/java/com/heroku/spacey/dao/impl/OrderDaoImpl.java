package com.heroku.spacey.dao.impl;

import com.heroku.spacey.dao.OrderDao;
import com.heroku.spacey.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Date;

@Repository
@RequiredArgsConstructor
@PropertySource("classpath:sql/order_queries.properties")
public class OrderDaoImpl implements OrderDao {

    private final JdbcTemplate jdbcTemplate;

    @Value("${insert_order}")
    private String sqlInsertOrder;
    @Value("${add_product_to_order}")
    private String sqlInsertProductToOrders;


    @Override
    @Transactional
    public void insert(Order order) {
        Long orderStatusId = order.getOrderStatusId();
        Long userId = order.getUserId();
        String ordererFirstName = order.getOrdererFirstName();
        String ordererLastName = order.getOrdererLastName();
        String phoneNumber = order.getPhoneNumber();
        String city = order.getCity();
        String street = order.getStreet();
        String house = order.getHouse();
        String apartment = order.getApartment();
        Date dateTime = order.getDateTime();
        float overallPrice = order.getOverallPrice();
        String commentOrder = order.getCommentOrder();

        Object[] parameters = new Object[] {orderStatusId, userId, ordererFirstName, ordererLastName, phoneNumber,
                                            city, street, house, apartment, dateTime, overallPrice, commentOrder};

        jdbcTemplate.update(sqlInsertOrder, parameters);
    }

    @Override
    public void addProductToOrder(Long orderId, Long productId, int amount, float sum) {
        jdbcTemplate.update(sqlInsertProductToOrders, orderId, productId, amount, sum);
    }
}
