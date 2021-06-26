package com.heroku.spacey.controllers;

import com.heroku.spacey.services.OrderService;
import com.heroku.spacey.dto.order.CreateOrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
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
