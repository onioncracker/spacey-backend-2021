package com.heroku.spacey.dao.impl;

import com.heroku.spacey.dao.EmployeeDao;
import com.heroku.spacey.dao.common.EmployeeQueryAdapter;
import com.heroku.spacey.dto.employee.EmployeeDto;
import com.heroku.spacey.mapper.EmployeeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
@PropertySource("classpath:sql/employee_queries.properties")
public class EmployeeDaoImpl implements EmployeeDao {

    private final JdbcTemplate jdbcTemplate;
    private final EmployeeQueryAdapter employeeQueryAdapter;

    @Value("${select_employees}")
    private String sqlSelectEmployees;
    @Value("${select_employee_by_id}")
    private String sqlSelectEmployeeById;
    @Value("${insert_employee}")
    private String sqlInsertEmployee;
    @Value("${update_employee}")
    private String sqlUpdateEmployee;
    @Value("${delete_employee}")
    private String sqlDeleteEmployee;


    @Override
    public List<EmployeeDto> getAll(Map<String, String> filters, int page, int pageSize) {

        employeeQueryAdapter
                .createSelect(sqlSelectEmployees)
                .addFilters(filters)
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

        ResultSetExtractor<EmployeeDto> rse = resultSet -> {
            EmployeeDto employeeDto = new EmployeeDto();
            EmployeeMapper.mapEmployee(resultSet, employeeDto);

            return employeeDto;
        };

        return jdbcTemplate.query(sqlSelectEmployeeById, rse, loginId);
    }

    @Override
    public void insert(EmployeeDto employeeDto) {

        String email = employeeDto.getEmail();
        String userrole = employeeDto.getUserRole();
        String firstname = employeeDto.getFirstName();
        String lastname = employeeDto.getLastName();
        String status = employeeDto.getStatus();
        String phonenumber = employeeDto.getPhoneNumber();

        Object[] parameters = new Object[] {email, userrole, firstname, lastname, status, phonenumber};

        jdbcTemplate.update(sqlInsertEmployee, parameters);
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

        Object[] parameters = new Object[] {email, userrole, firstname, lastname, status, phonenumber, loginid};

        return jdbcTemplate.update(sqlUpdateEmployee, parameters);
    }

    @Override
    public int delete(int loginId) {

        return jdbcTemplate.update(sqlDeleteEmployee, loginId);
    }
}
