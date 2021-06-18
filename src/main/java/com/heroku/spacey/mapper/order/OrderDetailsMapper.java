package com.heroku.spacey.mapper.order;

import com.heroku.spacey.dto.order.OrderDetailsDto;
import com.heroku.spacey.dto.product.ProductOrderDto;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.webjars.NotFoundException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailsMapper implements ResultSetExtractor<OrderDetailsDto> {

    private List<ProductOrderDto> products;

    public OrderDetailsMapper(List<ProductOrderDto> products) {
        this.products = products;
    }

    @Override
    public OrderDetailsDto extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        OrderDetailsDto order = new OrderDetailsDto();

        if (!resultSet.next()) {
            throw new NotFoundException("Item not found");
        }

        order.setOrderId(resultSet.getLong("orderid"));
        order.setFirstName(resultSet.getString("ordenername"));
        order.setLastName(resultSet.getString("ordenersurname"));
        order.setPhoneNumber(resultSet.getString("phonenumber"));
        order.setCity(resultSet.getString("city"));
        order.setStreet(resultSet.getString("street"));
        order.setHouse(resultSet.getString("house"));
        order.setApartment(resultSet.getString("appartment"));
        order.setDateCreate(resultSet.getString("datecreate"));
        order.setDateDelivery(resultSet.getString("datedelivery"));
        order.setOverallPrice(resultSet.getFloat("overallprice"));
        order.setComment(resultSet.getString("commentorder"));
        order.setOrderStatus(resultSet.getString("status"));
        order.setProducts(products);

        return order;
    }
}
