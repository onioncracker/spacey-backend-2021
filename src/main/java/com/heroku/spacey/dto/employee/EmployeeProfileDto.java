package com.heroku.spacey.dto.employee;

import lombok.Data;

@Data
public class EmployeeProfileDto {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String roleName;
    private String statusName;
}
