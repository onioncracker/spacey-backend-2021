package com.heroku.spacey.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import com.heroku.spacey.services.OrderService;
import org.springframework.web.bind.annotation.*;
import com.heroku.spacey.dto.order.CreateOrderDto;
import org.springframework.security.access.annotation.Secured;

import java.sql.SQLException;

@RestController
// TODO: remove security
@Secured("ROLE_USER")
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;


    @PostMapping
    public HttpStatus createOrder(@RequestBody CreateOrderDto createOrderDto) throws SQLException {
        orderService.createOrder(createOrderDto);

        return HttpStatus.CREATED;
    }
}
