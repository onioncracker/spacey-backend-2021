package com.heroku.spacey.dao.impl;

import com.heroku.spacey.dao.EmployeeDao;
import com.heroku.spacey.dao.common.EmployeeQueryAdapter;
import com.heroku.spacey.dto.employee.EmployeeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
@PropertySource("classpath:sql/employee_queries.properties")
public class EmployeeDaoImpl implements EmployeeDao {

    private final JdbcTemplate jdbcTemplate;
    private final EmployeeQueryAdapter employeeQueryAdapter;

    @Value("${select_employees}")
    private String selectEmployees;
    @Value("${select_employee_by_id}")
    private String selectEmployeeById;
    @Value("${insert_employee}")
    private String insertEmployee;
    @Value("${update_employee}")
    private String updateEmployee;
    @Value("${delete_employee}")
    private String deleteEmployee;


    @Override
    public List<EmployeeDto> getAll(Map<String, String> filters, int page, int pageSize) {

        employeeQueryAdapter
                .createSelect(selectEmployees)
                .addFilter(filters)
                .setPage(page, pageSize);

        RowMapper<EmployeeDto> rowMapper = (rs, i) -> {
            EmployeeDto employeeDto = new EmployeeDto();

            employeeDto.setLoginId(rs.getInt("loginid"));
            employeeDto.setFirstName(rs.getString("firstname"));
            employeeDto.setLastName(rs.getString("lastname"));
            employeeDto.setEmail(rs.getString("email"));
            employeeDto.setPhoneNumber(rs.getString("phonenumber"));
            employeeDto.setUserRole(rs.getString("userrole"));
            employeeDto.setStatus(rs.getString("status"));

            return employeeDto;
        };

        return jdbcTemplate.query(employeeQueryAdapter.build(), rowMapper, employeeQueryAdapter.getParams().toArray());
    }

    @Override
    public EmployeeDto getById(int loginId) {

        EmployeeDto employeeDto = new EmployeeDto();
        ResultSetExtractor<EmployeeDto> rse = resultSet -> {
            if (!resultSet.next()) {
                throw new NotFoundException("Haven't found employee with such ID.");
            } else {
                employeeDto.setLoginId(resultSet.getInt("loginid"));
                employeeDto.setFirstName(resultSet.getString("firstname"));
                employeeDto.setLastName(resultSet.getString("lastname"));
                employeeDto.setEmail(resultSet.getString("email"));
                employeeDto.setPhoneNumber(resultSet.getString("phonenumber"));
                employeeDto.setUserRole(resultSet.getString("userrole"));
                employeeDto.setStatus(resultSet.getString("status"));
            }

            return employeeDto;
        };

        return jdbcTemplate.query(selectEmployeeById, rse, loginId);
    }

    @Override
    public void insert(EmployeeDto employeeDto) {

        String email = employeeDto.getEmail();
        String userrole = employeeDto.getUserRole();
        String firstname = employeeDto.getFirstName();
        String lastname = employeeDto.getLastName();
        String status = employeeDto.getStatus();
        String phonenumber = employeeDto.getPhoneNumber();

        jdbcTemplate.update(insertEmployee, email, userrole, firstname, lastname, status, phonenumber);
    }

    @Override
    public int update(EmployeeDto employeeDto) {

        int loginid = employeeDto.getLoginId();
        String email = employeeDto.getEmail();
        String userrole = employeeDto.getUserRole();
        String firstname = employeeDto.getFirstName();
        String lastname = employeeDto.getLastName();
        String status = employeeDto.getStatus();
        String phonenumber = employeeDto.getPhoneNumber();

        return jdbcTemplate.update(updateEmployee, email, userrole, firstname, lastname, status, phonenumber, loginid);
    }

    @Override
    public int delete(int loginId) {

        return jdbcTemplate.update(deleteEmployee, loginId);
    }
}
