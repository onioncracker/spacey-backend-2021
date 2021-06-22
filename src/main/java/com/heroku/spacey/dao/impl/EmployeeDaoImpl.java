package com.heroku.spacey.dao.impl;

import com.heroku.spacey.dao.EmployeeDao;
import com.heroku.spacey.dao.common.EmployeeQueryAdapter;
import com.heroku.spacey.dto.employee.EmployeeDto;
import com.heroku.spacey.mapper.EmployeeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
@PropertySource("classpath:sql/employee_queries.properties")
public class EmployeeDaoImpl implements EmployeeDao {
    private final EmployeeMapper mapper = new EmployeeMapper();
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

        List<EmployeeDto> employeeDtos = Objects.requireNonNull(jdbcTemplate).query(employeeQueryAdapter.build(),
                mapper, employeeQueryAdapter.getParams().toArray());

        if (employeeDtos.isEmpty()) {
            return new ArrayList<>();
        }

        return employeeDtos;
    }

    @Override
    public EmployeeDto getById(Long userId) {
        List<EmployeeDto> employeeDtos = Objects.requireNonNull(jdbcTemplate).query(sqlSelectEmployeeById, mapper, userId);

        if (employeeDtos.isEmpty()) {
            throw new NotFoundException("Haven't found employee with such ID.");
        }

        return employeeDtos.get(0);
    }

    @Override
    public void insert(EmployeeDto employeeDto) {
        Long roleid = employeeDto.getRoleId();
        Long statusid = employeeDto.getStatusId();
        String email = employeeDto.getEmail();
        String firstname = employeeDto.getFirstName();
        String lastname = employeeDto.getLastName();
        String phonenumber = employeeDto.getPhoneNumber();

        Object[] parameters = new Object[] {roleid, statusid, email, firstname, lastname, phonenumber};

        jdbcTemplate.update(sqlInsertEmployee, parameters);
    }

    @Override
    public int update(EmployeeDto employeeDto) {
        Long roleid = employeeDto.getRoleId();
        Long statusid = employeeDto.getStatusId();
        String email = employeeDto.getEmail();
        String firstname = employeeDto.getFirstName();
        String lastname = employeeDto.getLastName();
        String phonenumber = employeeDto.getPhoneNumber();
        Long userid = employeeDto.getUserId();

        Object[] parameters = new Object[] {roleid, statusid, email, firstname, lastname, phonenumber, userid};

        return jdbcTemplate.update(sqlUpdateEmployee, parameters);
    }

    @Override
    public int delete(Long userId) {
        return jdbcTemplate.update(sqlDeleteEmployee, userId);
    }
}
