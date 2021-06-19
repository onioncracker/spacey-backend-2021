package com.heroku.spacey.controllers;

import com.heroku.spacey.entity.OrderStatus;
import com.heroku.spacey.services.OrderStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/order-status")
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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public String addOrderStatus(@RequestBody OrderStatus orderStatus) throws SQLException {

        orderStatusService.createOrderStatus(orderStatus);

        return "New order status has been created.";
    }

    @PutMapping("/{orderStatusId}")
    public String editEmployee(@PathVariable Long orderStatusId, @RequestBody OrderStatus orderStatus)
            throws SQLException {

        orderStatus.setOrderStatusId(orderStatusId);
        orderStatusService.updateOrderStatus(orderStatus);

        return "Employee info has been updated";
    }

    @DeleteMapping("/{orderStatusId}")
    public String deleteEmployee(@PathVariable Long orderStatusId) throws SQLException {

        orderStatusService.deleteOrderStatus(orderStatusId);

        return "Employee has been deleted.";
    }
}
