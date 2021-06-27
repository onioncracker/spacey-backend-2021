package com.heroku.spacey.dao.impl;

import com.heroku.spacey.dao.EmployeeDao;
import com.heroku.spacey.dao.common.EmployeeQueryAdapter;
import com.heroku.spacey.mapper.EmployeeMapper;
import com.heroku.spacey.dto.employee.EmployeeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.webjars.NotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.util.Map;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
@PropertySource("classpath:sql/employee_queries.properties")
public class EmployeeDaoImpl implements EmployeeDao {

    private final EmployeeMapper mapper;
    private final JdbcTemplate jdbcTemplate;
    private final EmployeeQueryAdapter employeeQueryAdapter;

    @Value("${select_employees}")
    private String sqlSelectEmployees;
    @Value("${select_employee_by_id}")
    private String sqlSelectEmployeeById;
    @Value("${select_available_couriers}")
    private String sqlSelectAvailableCouriers;
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

        return Objects.requireNonNull(jdbcTemplate).query(employeeQueryAdapter.build(),
                                                          mapper,
                                                          employeeQueryAdapter.getParams().toArray());
    }

    @Override
    public EmployeeDto getById(Long userId) {
        List<EmployeeDto> employeeDtos = Objects.requireNonNull(jdbcTemplate).query(sqlSelectEmployeeById,
                                                                                    mapper,
                                                                                    userId);
        if (employeeDtos.isEmpty()) {
            throw new NotFoundException("Haven't found employee with such ID.");
        }

        return employeeDtos.get(0);
    }

    @Override
    public List<EmployeeDto> getAvailableCouriers(Timestamp dateDelivery) {
        return Objects.requireNonNull(jdbcTemplate).query(sqlSelectAvailableCouriers,
                                                          mapper,
                                                          dateDelivery);
    }

    @Override
    public Long insert(EmployeeDto employeeDto) {
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sqlInsertEmployee, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, employeeDto.getRoleId());
            ps.setLong(2, employeeDto.getStatusId());
            ps.setLong(3, employeeDto.getTokenId());
            ps.setString(4, employeeDto.getEmail());
            ps.setString(5, employeeDto.getFirstName());
            ps.setString(6, employeeDto.getLastName());
            ps.setString(7, employeeDto.getPhoneNumber());

            return ps;
        }, holder);

        return (Long) Objects.requireNonNull(holder.getKeys()).get("userid");
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
