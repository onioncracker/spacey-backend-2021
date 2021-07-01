package com.heroku.spacey.dto.employee;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.heroku.spacey.utils.validators.NameConstraint;
import com.heroku.spacey.utils.validators.EmailConstraint;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    @Min(0)
    private Long userId;
    @EmailConstraint
    private String email;

    @NotNull
    private Long roleId;
    private String roleName;

    @NameConstraint
    private String firstName;
    @NameConstraint
    private String lastName;

    private Long tokenId;

    @NotNull
    private Long statusId;
    private String statusName;

    private String phoneNumber;
}
