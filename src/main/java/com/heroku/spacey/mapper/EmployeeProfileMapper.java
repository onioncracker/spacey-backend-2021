package com.heroku.spacey.mapper;

import com.heroku.spacey.dto.employee.EmployeeProfileDto;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.webjars.NotFoundException;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class EmployeeProfileMapper implements ResultSetExtractor<EmployeeProfileDto> {

    @Override
    public EmployeeProfileDto extractData(ResultSet rs) throws SQLException {
        EmployeeProfileDto employee = new EmployeeProfileDto();

        if (!rs.next()) {
            throw new NotFoundException("Employee not found!");
        }

        employee.setFirstName(rs.getString("firstname"));
        employee.setLastName(rs.getString("lastname"));
        employee.setEmail(rs.getString("email"));
        employee.setPhoneNumber(rs.getString("phonenumber"));
        employee.setRoleName(rs.getString("rolename"));
        employee.setStatusName(rs.getString("statusname"));

        return employee;
    }
}
