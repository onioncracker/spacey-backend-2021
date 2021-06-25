package com.heroku.spacey.dto.order;

import lombok.Data;

import java.util.List;
import java.sql.Timestamp;


@Data
public class CreateOrderDto {
    private List<ProductCreateOrderDto> products;

    private Long orderStatusId;

    private String ordererFirstName;
    private String ordererLastName;
    private String phoneNumber;
    private String city;
    private String street;
    private String house;
    private String apartment;
    private Timestamp dateCreate;
    private Timestamp dateDelivery;
    private float overallPrice;
    private String commentOrder;

    private boolean doNotDisturb;
    private boolean noContact;
}
