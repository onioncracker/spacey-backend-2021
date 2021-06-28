package com.heroku.spacey.entity;

import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class Order {
    private Long orderId;

    private List<Product> products;

    private String orderStatus;
    private Long orderStatusId;

    private Long userId;
    private String ordererFirstName;
    private String ordererLastName;
    private String phoneNumber;
    private String city;
    private String street;
    private String house;
    private String apartment;
    private Date dateTime;
    private float overallPrice;
    private String commentOrder;
}



