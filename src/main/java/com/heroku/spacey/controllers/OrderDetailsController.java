package com.heroku.spacey.controllers;

import com.heroku.spacey.dto.order.OrderDetailsDto;
import com.heroku.spacey.services.OrderDetailsService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderDetailsController {

    private final OrderDetailsService orderDetailsService;

    @Secured("COURIER")
    @GetMapping("/{orderId}")
    public OrderDetailsDto getOrderDetailsById(@PathVariable Long orderId) throws SQLException {
        return orderDetailsService.getOrderDetails(orderId);
    }
}
