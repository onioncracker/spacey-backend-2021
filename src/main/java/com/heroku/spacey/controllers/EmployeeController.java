package com.heroku.spacey.controllers;

import com.heroku.spacey.dto.employee.EmployeeDto;
import com.heroku.spacey.dto.registration.ResetPasswordDto;
import com.heroku.spacey.entity.User;
import com.heroku.spacey.services.UserService;
import com.heroku.spacey.services.EmployeeService;
import com.heroku.spacey.services.PasswordService;
import lombok.RequiredArgsConstructor;
import org.webjars.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.annotation.Secured;

import java.util.List;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;
import javax.validation.constraints.Size;

@Validated
@RestController
@Secured("ROLE_ADMIN")
@RequiredArgsConstructor
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    private final UserService userService;
    private final PasswordService passwordService;
    private final EmployeeService employeeService;


    @GetMapping
    public List<EmployeeDto> getEmployees(
            @RequestParam(defaultValue = "${default_page_num}") int page,
            @RequestParam(defaultValue = "${default_page_size}") int pagesize,
            @RequestParam(required = false) String roleid,
            @RequestParam(required = false) String statusid) throws SQLException {
        return employeeService.getEmployees(page, pagesize, roleid, statusid, null);
    }

    @GetMapping("/{userId}")
    public EmployeeDto getEmployeeById(@PathVariable Long userId) throws NotFoundException, SQLException {
        return employeeService.getEmployeeById(userId);
    }

    @GetMapping("/search/{prompt}")
    public List<EmployeeDto> searchEmployeeByNameSurname(
            @PathVariable @Size(min = 2) String prompt,
            @RequestParam(defaultValue = "${default_page_num}") int page,
            @RequestParam(defaultValue = "${default_page_size}") int pagesize,
            @RequestParam(required = false) String roleid,
            @RequestParam(required = false) String statusid) throws SQLException {
        return employeeService.getEmployees(page, pagesize, roleid, statusid, prompt);
    }

    @PostMapping
    public HttpStatus addEmployee(@RequestBody @Validated EmployeeDto employeeDto) throws SQLException {
        employeeService.createEmployee(employeeDto);

        return HttpStatus.CREATED;
    }

    @GetMapping("/create-password")
    public HttpStatus showCreatePasswordPage(@RequestParam("token") String token) throws TimeoutException {
        String tokenValidation = passwordService.validatePasswordResetToken(token);
        if (tokenValidation == null) {
            throw new com.amazonaws.services.apigateway.model.NotFoundException("User token is incorrect!");
        }
        return HttpStatus.OK;
    }

    @PostMapping("/create-password-save")
    public HttpStatus saveCreatePassword(@RequestBody @Validated ResetPasswordDto resetPasswordDto) {
        User user = userService.getUserByEmail(resetPasswordDto.getEmail());
        if (user == null) {
            throw new com.amazonaws.services.apigateway.model.NotFoundException("User not found!");
        }
        passwordService.saveResetPassword(user, resetPasswordDto);
        return HttpStatus.OK;
    }

    @PutMapping("/{userId}")
    public HttpStatus editEmployee(@PathVariable Long userId, @RequestBody @Validated EmployeeDto employeeDto)
            throws SQLException {
        employeeDto.setUserId(userId);
        employeeService.updateEmployee(employeeDto);

        return HttpStatus.OK;
    }

    @DeleteMapping("/{userId}")
    public HttpStatus deleteEmployee(@PathVariable Long userId) throws SQLException {
        employeeService.deleteEmployee(userId);

        return HttpStatus.OK;
    }
}
