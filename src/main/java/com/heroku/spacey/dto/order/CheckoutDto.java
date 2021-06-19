package com.heroku.spacey.dto.order;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CheckoutDto {
    private List<ProductCheckoutDto> products = new ArrayList<>();

    private float overallPrice;

    private Long userId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String city;
    private String street;
    private String house;
    private String apartment;
}
