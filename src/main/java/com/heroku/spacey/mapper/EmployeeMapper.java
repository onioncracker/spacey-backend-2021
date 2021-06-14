package com.heroku.spacey.mapper;

import com.heroku.spacey.dto.employee.EmployeeDto;
import org.webjars.NotFoundException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeMapper {
    public static void mapEmployee(ResultSet resultSet, EmployeeDto employeeDto) throws SQLException {
        if (!resultSet.next()) {
            throw new NotFoundException("Haven't found employee with such ID.");
        } else {
            employeeDto.setUserId(resultSet.getLong("userid"));
            employeeDto.setFirstName(resultSet.getString("firstname"));
            employeeDto.setLastName(resultSet.getString("lastname"));
            employeeDto.setEmail(resultSet.getString("email"));
            employeeDto.setPhoneNumber(resultSet.getString("phonenumber"));
            employeeDto.setRoleId(resultSet.getLong("roleid"));
            employeeDto.setRoleName(resultSet.getString("rolename"));
            employeeDto.setStatusId(resultSet.getLong("statusid"));
            employeeDto.setStatusName(resultSet.getString("statusname"));
        }
    }
}
