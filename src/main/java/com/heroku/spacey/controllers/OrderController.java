package com.heroku.spacey.controllers;

import com.heroku.spacey.dto.order.CreateOrderDto;
import com.heroku.spacey.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public String createOrder(@RequestBody CreateOrderDto createOrderDto) throws SQLException {
        orderService.createOrder(createOrderDto);

        return "New order has been created.";
    }
}
