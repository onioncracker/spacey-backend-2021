package com.heroku.spacey.dto.order;

import lombok.Data;

@Data
public class AllOrderStatusDto {
    private Long orderStatusId;
    private String status;
}
