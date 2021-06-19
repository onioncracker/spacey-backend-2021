package com.heroku.spacey.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.heroku.spacey.dto.order.CourierOrdersDto;
import com.heroku.spacey.services.CourierOrdersService;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/profile/orders")
public class CourierOrdersController {

    private final CourierOrdersService courierOrdersService;

    @GetMapping("/{id}")
    public List<CourierOrdersDto> getOrders(
            @PathVariable Long id,
            @RequestParam(required = false) Date date) {
        return courierOrdersService.getCourierOrders(id, date);
    }
}
