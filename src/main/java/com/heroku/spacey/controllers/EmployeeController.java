package com.heroku.spacey.controllers;

import com.heroku.spacey.dto.employee.EmployeeDto;
import com.heroku.spacey.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import javax.validation.constraints.Size;
import java.sql.SQLException;
import java.util.List;

@PreAuthorize("hasAuthority(T(com.heroku.spacey.utils.Role).ADMIN)")
@RestController
@Validated
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;


    @GetMapping
    public List<EmployeeDto> getEmployees(
            @RequestParam(required = false) String page,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String status) throws SQLException {

        return employeeService.getEmployees(page, role, status, null);
    }

    @GetMapping("/{loginid}")
    public EmployeeDto getEmployeeById(@PathVariable int loginid) throws NotFoundException, SQLException {

        return employeeService.getEmployeeById(loginid);
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

    @PutMapping("/{loginid}")
    public String editEmployee(@PathVariable int loginid, @RequestBody @Validated EmployeeDto employeeDto)
            throws SQLException {

        employeeDto.setLoginId(loginid);
        employeeService.updateEmployee(employeeDto);

        return "Employee info has been updated";
    }

    @DeleteMapping("/{loginid}")
    public String deleteEmployee(@PathVariable int loginid) throws SQLException {

        employeeService.deleteEmployee(loginid);

        return "Employee has been deleted.";
    }
}
