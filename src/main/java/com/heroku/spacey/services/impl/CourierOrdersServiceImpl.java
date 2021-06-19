package com.heroku.spacey.services.impl;

import com.heroku.spacey.dao.OrderDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.heroku.spacey.dto.order.CourierOrdersDto;
import com.heroku.spacey.services.CourierOrdersService;

import java.sql.Date;

import java.util.List;
import java.util.Calendar;

@Service
@RequiredArgsConstructor
public class CourierOrdersServiceImpl implements CourierOrdersService {

    private final OrderDao orders;

    public List<CourierOrdersDto> getCourierOrders(Long id, Date date) {

        if (date == null) {
            return orders.getCourierOrders(id, new Date(Calendar.getInstance().getTime().getTime()));
        } else {
            return orders.getCourierOrders(id, date);
        }
    }
}
