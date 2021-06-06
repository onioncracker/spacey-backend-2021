package com.heroku.spacey.controllers;

import com.heroku.spacey.dto.employee.EmployeeDto;
import com.heroku.spacey.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;


    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAll(
            @RequestParam(required = false) String page,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String status) {

        try {
            List<EmployeeDto> employees = employeeService.getEmployees(page, role, status, null);

            return ResponseEntity.ok(employees);

        } catch (SQLException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{loginid}")
    public ResponseEntity<EmployeeDto> get(@PathVariable int loginid) {
        try {
            EmployeeDto employeeDto = employeeService.getEmployeeById(loginid);

            return ResponseEntity.ok(employeeDto);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (SQLException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/search/{prompt}")
    public ResponseEntity<List<EmployeeDto>> searchEmployeeByNameSurname(
            @PathVariable String prompt,
            @RequestParam(required = false) String page,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String status) {

        try {
            List<EmployeeDto> employees;

            if (prompt.length() >= 2) {
                employees = employeeService.getEmployees(page, role, status, prompt);

                if (employees != null) {
                    return ResponseEntity.ok(employees);
                } else {
                    return ResponseEntity.notFound().build();
                }
            } else {
                return ResponseEntity.badRequest().build();
            }

        } catch (SQLException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody EmployeeDto employeeDto) {

        try {
            employeeService.createEmployee(employeeDto);

            return ResponseEntity.ok("New employee has been created.");

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }

    @PutMapping("/{loginid}")
    public ResponseEntity<String> update(@PathVariable int loginid, @RequestBody EmployeeDto employeeDto) {
        try {
            employeeDto.setLoginId(loginid);

            if (employeeService.updateEmployee(employeeDto) == 0) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok("Employee info has been updated");

        } catch (SQLException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{loginid}")
    public ResponseEntity<String> delete(@PathVariable int loginid) {
        try {
            if (employeeService.deleteEmployee(loginid) == 0) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok("Employee has been deleted.");

        } catch (SQLException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
