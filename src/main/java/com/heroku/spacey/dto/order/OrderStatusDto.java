package com.heroku.spacey.dto.order;

import lombok.Data;

@Data
public class OrderStatusDto {
    private Long orderId;
    private Long orderStatusId;
}
