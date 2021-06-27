package com.heroku.spacey.services;

import com.heroku.spacey.dto.order.CreateOrderDto;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public interface OrderService {

    void createOrder(CreateOrderDto createOrderDto) throws IllegalArgumentException, SQLException, NoSuchAlgorithmException;
}
