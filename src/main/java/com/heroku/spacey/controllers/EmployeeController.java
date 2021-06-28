package com.heroku.spacey.controllers;

import com.heroku.spacey.dto.employee.EmployeeDto;
import com.heroku.spacey.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.webjars.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.annotation.Secured;

import java.util.List;
import java.sql.SQLException;
import javax.validation.constraints.Size;

@Validated
@RestController
@Secured("ROLE_ADMIN")
@RequiredArgsConstructor
@RequestMapping("/api/v1/employees")
public class EmployeeController {

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
