package com.heroku.spacey.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import com.heroku.spacey.services.OrderService;
import org.springframework.web.bind.annotation.*;
import com.heroku.spacey.dto.order.CreateOrderDto;
import org.springframework.security.access.annotation.Secured;

import java.sql.SQLException;

@RestController
@Secured("ROLE_USER")
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public String createOrder(@RequestBody CreateOrderDto createOrderDto) throws SQLException {
        orderService.createOrder(createOrderDto);

        return "New order has been created.";
    }
}
