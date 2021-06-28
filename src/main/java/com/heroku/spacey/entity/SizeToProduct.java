package com.heroku.spacey.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SizeToProduct {
    private Long sizeId;
    private Long productId;
    private Long quantity;
}
