package com.heroku.spacey.mapper;

import com.heroku.spacey.dto.order.CourierOrdersDto;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CourierOrdersMapper implements RowMapper<CourierOrdersDto> {

    @Override
    public CourierOrdersDto mapRow(ResultSet resultSet, int i) throws SQLException {

        CourierOrdersDto courierOrdersDto = new CourierOrdersDto();

        courierOrdersDto.setOrderId(resultSet.getLong("orderid"));
        courierOrdersDto.setOrdenerName(resultSet.getString("ordenername"));
        courierOrdersDto.setOrdenerSurname(resultSet.getString("ordenersurname"));
        courierOrdersDto.setPhoneNumber(resultSet.getString("phonenumber"));
        courierOrdersDto.setStatus(resultSet.getString("status"));
        courierOrdersDto.setDateTime(resultSet.getDate("datetime"));
        courierOrdersDto.setCity(resultSet.getString("city"));
        courierOrdersDto.setStreet(resultSet.getString("street"));
        courierOrdersDto.setHouse(resultSet.getString("house"));
        courierOrdersDto.setApartment(resultSet.getString("appartment"));

        return courierOrdersDto;
    }
}
