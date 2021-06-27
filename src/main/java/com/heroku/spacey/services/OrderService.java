package com.heroku.spacey.services;

import com.heroku.spacey.dto.order.CreateOrderDto;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public interface OrderService {

    void createOrderForAuthorizedUser(CreateOrderDto createOrderDto) throws IllegalArgumentException,
                                                                            SQLException,
                                                                            NoSuchAlgorithmException;

    void createOrderForAnonymousUser(CreateOrderDto createOrderDto) throws IllegalArgumentException,
                                                                              SQLException,
                                                                              NoSuchAlgorithmException;
}
