package com.heroku.spacey.entity;

import lombok.Data;

@Data
public class OrderStatus {
    private Long orderStatusId;
    private String status;
}
