package com.heroku.spacey.controllers;

import org.springframework.security.access.annotation.Secured;
import org.webjars.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.heroku.spacey.dto.employee.EmployeeDto;
import com.heroku.spacey.services.EmployeeService;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.sql.SQLException;
import javax.validation.constraints.Size;

@Validated
@Secured("ADMIN")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;


    @GetMapping
    public List<EmployeeDto> getEmployees(
            @RequestParam(required = false) String page,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String status) throws SQLException {
        return employeeService.getEmployees(page, role, status, null);
    }

    @GetMapping("/{userId}")
    public EmployeeDto getEmployeeById(@PathVariable Long userId) throws NotFoundException, SQLException {
        return employeeService.getEmployeeById(userId);
    }

    @GetMapping("/search/{prompt}")
    public List<EmployeeDto> searchEmployeeByNameSurname(
            @PathVariable @Size(min = 2) String prompt,
            @RequestParam(required = false) String page,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String status) throws SQLException {
        return employeeService.getEmployees(page, role, status, prompt);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public String addEmployee(@RequestBody @Validated EmployeeDto employeeDto) throws SQLException {
        employeeService.createEmployee(employeeDto);

        return "New employee has been created.";
    }

    @PutMapping("/{userId}")
    public String editEmployee(@PathVariable Long userId, @RequestBody @Validated EmployeeDto employeeDto)
            throws SQLException {
        employeeDto.setUserId(userId);
        employeeService.updateEmployee(employeeDto);

        return "Employee info has been updated";
    }

    @DeleteMapping("/{userId}")
    public String deleteEmployee(@PathVariable Long userId) throws SQLException {
        employeeService.deleteEmployee(userId);

        return "Employee has been deleted.";
    }
}
