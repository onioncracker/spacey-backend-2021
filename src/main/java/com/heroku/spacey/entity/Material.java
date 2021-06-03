package com.heroku.spacey.entity;

import lombok.Data;

import java.util.List;

@Data
public class Material {
    private Integer id;
    private String name;
    private List<Product> products;
}
