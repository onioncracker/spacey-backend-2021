package com.heroku.spacey.services.impl;

import com.heroku.spacey.dao.EmployeeDao;
import com.heroku.spacey.dto.employee.EmployeeDto;
import com.heroku.spacey.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    // default values
    private static final int DEFAULT_PAGE_NUM = 0;
    private static final int DEFAULT_PAGE_SIZE = 20;

    private final EmployeeDao employeeDao;


    public List<EmployeeDto> getEmployees(String page, String role, String status, String searchPrompt)
            throws SQLException {

        Map<String, String> filters = new HashMap<>();

        int pageNum = DEFAULT_PAGE_NUM;
        int pageSize = DEFAULT_PAGE_SIZE;

        if (isValid(role)) {
            filters.put("role", role);
        }

        if (isValid(status)) {
            filters.put("status", status);
        }

        if (isValid(page)) {
            String[] pageProps = page.split("-");
            pageNum = Integer.parseInt(pageProps[0]);
            pageSize = Integer.parseInt(pageProps[1]);
        }

        if (isValid(searchPrompt) && searchPrompt.trim().length() >= 2) {
            filters.put("search", searchPrompt);
        }

        return employeeDao.getAll(filters, pageNum, pageSize);
    }

    public EmployeeDto getEmployeeById(int loginId) throws NotFoundException, SQLException {
        return employeeDao.getById(loginId);
    }

    public void createEmployee(EmployeeDto employeeDto) throws SQLException {
        if (isValid(employeeDto.getEmail()) && isValid(employeeDto.getUserRole())
                && isValid(employeeDto.getFirstName()) && isValid(employeeDto.getLastName())
                && isValid(employeeDto.getStatus())) {
            employeeDao.insert(employeeDto);
        } else {
            throw new SQLException("Fill in all mandatory fields.");
        }
    }

    public int updateEmployee(EmployeeDto employeeDto) throws SQLException {
        if (isValid(employeeDto.getEmail()) && isValid(employeeDto.getUserRole())
                && isValid(employeeDto.getFirstName()) && isValid(employeeDto.getLastName())
                && isValid(employeeDto.getStatus())) {
            return employeeDao.update(employeeDto);
        } else {
            throw new SQLException("Fill in all mandatory fields.");
        }
    }

    public int deleteEmployee(int loginId) throws SQLException {
        return employeeDao.delete(loginId);
    }

    private boolean isValid(String value) {
        return value != null && !value.isEmpty();
    }
}
