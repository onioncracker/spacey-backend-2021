package com.heroku.spacey.entity;

import lombok.Data;

@Data
public class SizeToProduct {
    private Long sizeId;
    private Long productId;
    private Long quantity;
}
