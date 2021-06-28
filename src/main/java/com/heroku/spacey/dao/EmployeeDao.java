package com.heroku.spacey.dao;

import com.heroku.spacey.dto.employee.EmployeeDto;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface EmployeeDao {

    List<EmployeeDto> getAll(Map<String, String> filters, int pageNum, int pageSize);

    EmployeeDto getById(Long userId);

    List<EmployeeDto> getAvailableCouriers(Timestamp dateDelivery) throws SQLException;

    Long insert(EmployeeDto employeeDto);

    int update(EmployeeDto employeeDto);

    int delete(Long userId);
}
