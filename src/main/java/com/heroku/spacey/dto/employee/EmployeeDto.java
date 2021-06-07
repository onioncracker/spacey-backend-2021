package com.heroku.spacey.dto.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployeeDto {

    private int loginId;
    private String email;
    private String userRole;
    private String firstName;
    private String lastName;
    private String status;
    private String phoneNumber;
}
