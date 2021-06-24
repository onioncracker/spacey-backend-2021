package com.heroku.spacey.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderStatus {
    private Long orderStatusId;
    private String status;
}
