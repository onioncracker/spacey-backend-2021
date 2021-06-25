package com.heroku.spacey.dto.product;

import lombok.Data;

@Data
public class SizeDto {
    private Long id;
    private String name;
    private boolean isAvailable;
}
