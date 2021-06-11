package com.heroku.spacey.entity;

import lombok.Data;

import java.util.List;

@Data
public class Size {
    private Long id;
    private String name;
    private List<Product> products;
}
