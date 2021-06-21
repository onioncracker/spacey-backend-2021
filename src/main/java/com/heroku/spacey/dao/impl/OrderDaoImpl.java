package com.heroku.spacey.dao.impl;

import com.heroku.spacey.dao.OrderDao;
import lombok.RequiredArgsConstructor;
import com.heroku.spacey.dto.order.CreateOrderDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.PreparedStatement;

@Repository
@RequiredArgsConstructor
@PropertySource("classpath:sql/order_queries.properties")
public class OrderDaoImpl implements OrderDao {

    private final JdbcTemplate jdbcTemplate;

    @Value("${insert_order}")
    private String sqlInsertOrder;
    @Value("${add_user_to_orders}")
    private String sqlInsertUserToOrders;
    @Value("${add_product_to_order}")
    private String sqlInsertProductToOrders;


    @Override
    @Transactional
    public Long insert(CreateOrderDto createOrderDto, Timestamp dateCreate, Long userId) {
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sqlInsertOrder, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, createOrderDto.getOrderStatusId());
            ps.setString(2, createOrderDto.getOrdererFirstName());
            ps.setString(3, createOrderDto.getOrdererLastName());
            ps.setString(4, createOrderDto.getPhoneNumber());
            ps.setString(5, createOrderDto.getCity());
            ps.setString(6, createOrderDto.getStreet());
            ps.setString(7, createOrderDto.getHouse());
            ps.setString(8, createOrderDto.getApartment());
            ps.setTimestamp(9, dateCreate);
            ps.setTimestamp(10, createOrderDto.getDateDelivery());
            ps.setFloat(11, createOrderDto.getOverallPrice());
            ps.setString(12, createOrderDto.getCommentOrder());

            return ps;
        }, holder);

        return (Long) Objects.requireNonNull(holder.getKeys()).get("orderid");
    }

    @Override
    public void addUserToOrders(Long orderId, Long userId) {
        jdbcTemplate.update(sqlInsertUserToOrders, orderId, userId);
    }

    @Override
    public void addProductToOrder(Long orderId, Long productId, Long sizeId, int amount, float sum) {
        jdbcTemplate.update(sqlInsertProductToOrders, orderId, productId, sizeId, amount, sum);
    }
}
