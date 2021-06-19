package com.heroku.spacey.dto.order;

import lombok.Data;

import java.sql.Date;

@Data
public class CourierOrdersDto {
    private Long orderId;
    private String ordenerName;
    private String ordenerSurname;
    private String phoneNumber;
    private String status;
    private Date dateTime;
    private String city;
    private String street;
    private String house;
    private String apartment;
}
