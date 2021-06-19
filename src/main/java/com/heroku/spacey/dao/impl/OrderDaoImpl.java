package com.heroku.spacey.dao.impl;

import com.heroku.spacey.dao.OrderDao;
import com.heroku.spacey.dto.order.CreateOrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Objects;

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
    public Long insert(CreateOrderDto createOrderDto, Timestamp orderTime, Timestamp deliveryTime) {
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sqlInsertOrder, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, createOrderDto.getOrderStatusId());
            ps.setLong(2, createOrderDto.getUserId());
            ps.setString(3, createOrderDto.getOrdererFirstName());
            ps.setString(4, createOrderDto.getOrdererLastName());
            ps.setString(5, createOrderDto.getPhoneNumber());
            ps.setString(6, createOrderDto.getCity());
            ps.setString(7, createOrderDto.getStreet());
            ps.setString(8, createOrderDto.getHouse());
            ps.setString(9, createOrderDto.getApartment());
            ps.setTimestamp(10, orderTime);
            ps.setTimestamp(11, createOrderDto.getDateDelivery());
            ps.setFloat(12, createOrderDto.getOverallPrice());
            ps.setString(13, createOrderDto.getCommentOrder());

            return ps;
        }, holder);

        return (Long) Objects.requireNonNull(holder.getKeys()).get("orderid");
    }

    @Override
    public void addProductToOrder(Long orderId, Long productId, int amount, float sum) {
        jdbcTemplate.update(sqlInsertProductToOrders, orderId, productId, amount, sum);
    }
}
