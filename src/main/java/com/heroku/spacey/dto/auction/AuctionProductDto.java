package com.heroku.spacey.dto.auction;

import lombok.Data;

import java.util.Set;

@Data
public class AuctionProductDto {
    private Long id;
    private String name;
    private String productSex;
    private String photo;
    private String description;
    private String category;
    private String color;
    private Set<String> materials;
}
