package com.heroku.spacey.dto.employee;

import com.heroku.spacey.utils.validators.EmailConstraint;
import com.heroku.spacey.utils.validators.NameConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployeeDto {

    @Min(0)
    private int loginId;
    @EmailConstraint
    private String email;
    @NotNull
    @NotBlank
    private String userRole;
    @NameConstraint
    private String firstName;
    @NameConstraint
    private String lastName;
    @NotNull
    @NotBlank
    private String status;
    private String phoneNumber;
}
