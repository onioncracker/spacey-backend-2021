package com.heroku.spacey.dto.cart;

import lombok.Data;

@Data
public class EditCartDto {
    private long productId;
    private int amount;
}
