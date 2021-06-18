package com.heroku.spacey.dto.order;

import com.heroku.spacey.dto.product.ProductOrderDto;

import lombok.Data;
import java.util.ArrayList;

@Data
public class OrderDetailsDto {
    private Long orderId;
    private String orderStatus;
    private String dateCreate;
    private String dateDelivery;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String city;
    private String street;
    private String house;
    private String apartment;
    private float overallPrice;
    private String comment;
    private ArrayList<ProductOrderDto> products;
}
