package com.heroku.spacey.dao.impl;

import com.heroku.spacey.dao.OrderDao;
import com.heroku.spacey.dto.order.CreateOrderDto;
import com.heroku.spacey.dto.order.CourierOrdersDto;
import com.heroku.spacey.mapper.order.CourierOrdersMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
@PropertySource("classpath:sql/order_queries.properties")
public class OrderDaoImpl implements OrderDao {

    private final JdbcTemplate jdbcTemplate;
    private final CourierOrdersMapper courierOrdersMapper;

    @Value("${insert_order}")
    private String sqlInsertOrder;
    @Value("${add_product_to_order}")
    private String sqlInsertProductToOrders;
    @Value("${add_user_to_orders}")
    private String sqlInsertUserToOrders;
    @Value("${get_courier_orders}")
    private String getCourierOrders;


    @Override
    @Transactional
    public Long insert(CreateOrderDto createOrderDto) {
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
            ps.setTimestamp(9, createOrderDto.getDateCreate());
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

    @Override
    public List<CourierOrdersDto> getCourierOrders(Long orderId, Date date) {
        LocalDateTime startOfDay = date.toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusSeconds(1);

        return jdbcTemplate.query(getCourierOrders, courierOrdersMapper, orderId, startOfDay, endOfDay);
    }
}
