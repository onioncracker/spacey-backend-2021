package com.heroku.spacey.dao;

import com.heroku.spacey.dto.employee.EmployeeDto;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface EmployeeDao {

    EmployeeDto getById(int loginId) throws SQLException;

    List<EmployeeDto> getAll(Map<String, String> filters, int pageNum, int pageSize)
            throws SQLException;

    void insert(EmployeeDto employeeDto) throws SQLException;

    int update(EmployeeDto employeeDto) throws SQLException;

    int delete(int loginId) throws SQLException;
}
