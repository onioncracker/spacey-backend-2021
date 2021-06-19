package com.heroku.spacey.controllers;

import com.heroku.spacey.dto.order.OrderDetailsDto;
import com.heroku.spacey.dto.order.OrderStatusDto;
import com.heroku.spacey.services.OrderDetailsService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

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

    @Secured("COURIER")
    @PutMapping("/status/{orderId}")
    public HttpStatus editOrderStatus(@RequestBody OrderStatusDto orderStatusDto,
                                      @PathVariable Long orderId) throws  SQLException {
        OrderDetailsDto order = orderDetailsService.getOrderDetails(orderId);
        orderDetailsService.updateOrderStatus(orderStatusDto, order.getOrderId());
        return HttpStatus.OK;
    }
}
