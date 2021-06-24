package com.heroku.spacey.mapper;

import com.heroku.spacey.dto.employee.EmployeeDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeMapper implements RowMapper<EmployeeDto> {
    @Override
    public EmployeeDto mapRow(ResultSet resultSet, int i) throws SQLException {
        EmployeeDto employeeDto = new EmployeeDto();

        employeeDto.setUserId(resultSet.getLong("userid"));
        employeeDto.setFirstName(resultSet.getString("firstname"));
        employeeDto.setLastName(resultSet.getString("lastname"));
        employeeDto.setEmail(resultSet.getString("email"));
        employeeDto.setPhoneNumber(resultSet.getString("phonenumber"));
        employeeDto.setRoleId(resultSet.getLong("roleid"));
        employeeDto.setRoleName(resultSet.getString("rolename"));
        employeeDto.setStatusId(resultSet.getLong("statusid"));
        employeeDto.setStatusName(resultSet.getString("statusname"));

        return employeeDto;
    }
}
