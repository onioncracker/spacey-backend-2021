package com.heroku.spacey.controllers;

import com.heroku.spacey.entity.OrderStatus;
import com.heroku.spacey.services.OrderStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order-statuses")
@RequiredArgsConstructor
public class OrderStatusController {
    private final OrderStatusService orderStatusService;

    @GetMapping
    public List<OrderStatus> getOrderStatuses() {
        return orderStatusService.getOrderStatuses();
    }

    @GetMapping("/{orderStatusId}")
    public OrderStatus getOrderStatusById(@PathVariable Long orderStatusId) {
        return orderStatusService.getOrderStatusById(orderStatusId);
    }

    @GetMapping("/{status}")
    public OrderStatus getOrderStatusByName(@PathVariable String status) {
        return orderStatusService.getOrderStatusByName(status);
    }

    @PostMapping
    public HttpStatus addOrderStatus(@RequestBody OrderStatus orderStatus) {
        orderStatusService.createOrderStatus(orderStatus);

        return HttpStatus.CREATED;
    }

    @PutMapping("/{orderStatusId}")
    public HttpStatus editEmployee(@PathVariable Long orderStatusId, @RequestBody OrderStatus orderStatus) {
        orderStatus.setOrderStatusId(orderStatusId);
        orderStatusService.updateOrderStatus(orderStatus);

        return HttpStatus.OK;
    }

    @DeleteMapping("/{orderStatusId}")
    public HttpStatus deleteEmployee(@PathVariable Long orderStatusId) {
        orderStatusService.deleteOrderStatus(orderStatusId);

        return HttpStatus.OK;
    }
}
