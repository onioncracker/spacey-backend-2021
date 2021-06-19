package com.heroku.spacey.services;

import com.heroku.spacey.dto.order.CourierOrdersDto;

import java.sql.Date;
import java.util.List;

public interface CourierOrdersService {
    List<CourierOrdersDto> getCourierOrders(Long id, Date date);
}
