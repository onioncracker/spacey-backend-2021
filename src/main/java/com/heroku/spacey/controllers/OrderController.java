package com.heroku.spacey.controllers;

import com.heroku.spacey.services.OrderService;
import com.heroku.spacey.dto.order.CreateOrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class OrderController {

    private final OrderService orderService;


    @PostMapping("/order-authorized")
    public HttpStatus createOrderForAuthorizedUser(@RequestBody CreateOrderDto createOrderDto)
            throws SQLException, NoSuchAlgorithmException {

        orderService.createOrderForAuthorizedUser(createOrderDto);

        return HttpStatus.CREATED;
    }

    @PostMapping("/order-anonymous")
    public HttpStatus createOrderForAnonymousUser(@RequestBody CreateOrderDto createOrderDto)
            throws SQLException, NoSuchAlgorithmException {

        orderService.createOrderForAnonymousUser(createOrderDto);

        return HttpStatus.CREATED;
    }
}
