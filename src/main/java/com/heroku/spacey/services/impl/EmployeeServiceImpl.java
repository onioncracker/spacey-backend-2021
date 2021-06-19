package com.heroku.spacey.services.impl;

import com.heroku.spacey.dao.EmployeeDao;
import com.heroku.spacey.dto.employee.EmployeeDto;
import com.heroku.spacey.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
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

        if (!StringUtils.isBlank(role)) {
            filters.put("role", role);
        }

        if (!StringUtils.isBlank(status)) {
            filters.put("status", status);
        }

        int pageNum = DEFAULT_PAGE_NUM;
        int pageSize = DEFAULT_PAGE_SIZE;

        if (!StringUtils.isBlank(page)) {
            String[] pageProps = page.split("-");
            pageNum = Integer.parseInt(pageProps[0]);
            pageSize = Integer.parseInt(pageProps[1]);
        }

        if (!StringUtils.isBlank(searchPrompt)) {
            filters.put("search", searchPrompt);
        }

        return employeeDao.getAll(filters, pageNum, pageSize);
    }

    public EmployeeDto getEmployeeById(Long userId) throws NotFoundException, SQLException {
        return employeeDao.getById(userId);
    }

    public void createEmployee(EmployeeDto employeeDto) throws IllegalArgumentException, SQLException {
        employeeDao.insert(employeeDto);
    }

    public int updateEmployee(EmployeeDto employeeDto) throws IllegalArgumentException, SQLException {
        if (employeeDao.update(employeeDto) == 0) {
            throw new NotFoundException("Haven't found employee with such ID.");
        }

        return employeeDao.update(employeeDto);
    }

    public int deleteEmployee(Long userId) throws SQLException {
        if (employeeDao.delete(userId) == 0) {
            throw new NotFoundException("Haven't found employee with such ID.");
        }

        return employeeDao.delete(userId);
    }
}
