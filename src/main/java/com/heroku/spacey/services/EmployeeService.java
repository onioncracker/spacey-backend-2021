package com.heroku.spacey.services;

import com.heroku.spacey.dto.employee.EmployeeDto;
import org.webjars.NotFoundException;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeService {

    List<EmployeeDto> getEmployees(String page, String role, String status, String searchPrompt) throws SQLException;

    EmployeeDto getEmployeeById(int loginId) throws NotFoundException, SQLException;

    void createEmployee(EmployeeDto employeeDto) throws IllegalArgumentException, SQLException;

    int updateEmployee(EmployeeDto employeeDto) throws IllegalArgumentException, SQLException;

    int deleteEmployee(int loginId) throws SQLException;
}
