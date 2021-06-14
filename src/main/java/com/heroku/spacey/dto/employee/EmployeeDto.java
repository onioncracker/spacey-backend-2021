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
    private Long userId;
    @EmailConstraint
    private String email;

    @NotNull
    private Long roleId;
    @NotNull
    @NotBlank
    private String roleName;

    @NameConstraint
    private String firstName;
    @NameConstraint
    private String lastName;

    @NotNull
    private Long statusId;
    @NotNull
    @NotBlank
    private String statusName;

    private String phoneNumber;
}
