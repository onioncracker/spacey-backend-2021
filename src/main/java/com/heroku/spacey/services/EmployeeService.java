package com.heroku.spacey.services;

import com.heroku.spacey.dto.employee.EmployeeDto;
import org.webjars.NotFoundException;

import java.sql.Timestamp;
import java.sql.SQLException;
import java.util.List;

public interface EmployeeService {

    List<EmployeeDto> getEmployees(int page, int pageSize,
                                   String roleId, String statusId, String searchPrompt) throws SQLException;

    EmployeeDto getEmployeeById(Long userId) throws NotFoundException, SQLException;

    List<EmployeeDto> getAvailableCouriers(Timestamp dateDelivery) throws SQLException;

    void createEmployee(EmployeeDto employeeDto) throws IllegalArgumentException, SQLException;

    int updateEmployee(EmployeeDto employeeDto) throws IllegalArgumentException, SQLException;

    int deleteEmployee(Long userId) throws SQLException;
}
