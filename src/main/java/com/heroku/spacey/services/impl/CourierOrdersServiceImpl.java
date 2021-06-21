package com.heroku.spacey.services.impl;

import com.heroku.spacey.dao.OrderDao;
import com.heroku.spacey.utils.security.SecurityUtils;
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
    private final SecurityUtils securityUtils;


    public List<CourierOrdersDto> getCourierOrders(Date date) {
        Long userId = securityUtils.getUserIdByToken();

        if (date == null) {
            return orders.getCourierOrders(userId, new Date(Calendar.getInstance().getTime().getTime()));
        } else {
            return orders.getCourierOrders(userId, date);
        }
    }
}
