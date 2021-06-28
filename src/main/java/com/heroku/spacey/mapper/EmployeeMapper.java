package com.heroku.spacey.mapper;

import com.heroku.spacey.dto.employee.EmployeeDto;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class EmployeeMapper implements RowMapper<EmployeeDto> {

    @Override
    public EmployeeDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        EmployeeDto employeeDto = new EmployeeDto();

        employeeDto.setUserId(rs.getLong("userid"));
        employeeDto.setFirstName(rs.getString("firstname"));
        employeeDto.setLastName(rs.getString("lastname"));
        employeeDto.setEmail(rs.getString("email"));
        employeeDto.setPhoneNumber(rs.getString("phonenumber"));
        employeeDto.setRoleId(rs.getLong("roleid"));
        employeeDto.setRoleName(rs.getString("rolename"));
        employeeDto.setStatusId(rs.getLong("statusid"));
        employeeDto.setStatusName(rs.getString("statusname"));

        return employeeDto;
    }
}
