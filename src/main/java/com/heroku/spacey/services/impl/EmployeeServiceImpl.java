package com.heroku.spacey.services.impl;

import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import com.heroku.spacey.dao.TokenDao;
import com.heroku.spacey.dao.EmployeeDao;
import com.heroku.spacey.dao.UserDao;
import com.heroku.spacey.dto.employee.EmployeeDto;
import com.heroku.spacey.entity.User;
import com.heroku.spacey.entity.Token;
import com.heroku.spacey.services.UserService;
import com.heroku.spacey.services.EmployeeService;
import com.heroku.spacey.utils.EmailComposer;
import com.heroku.spacey.utils.Status;
import com.heroku.spacey.utils.registration.OnRegistrationCompleteEvent;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.PropertySource;
import org.webjars.NotFoundException;
import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.sql.SQLException;
import java.util.Map;
import java.util.List;
import java.util.UUID;
import java.util.HashMap;

@Slf4j
@Service
@RequiredArgsConstructor
@PropertySource("classpath:const.properties")
public class EmployeeServiceImpl implements EmployeeService {

    private final UserDao userDao;
    private final TokenDao tokenDao;
    private final EmployeeDao employeeDao;
    private final UserService userService;
    private final ApplicationEventPublisher eventPublisher;

    @Value("${create_password_url}")
    private String createPasswordUrl;
    @Value("${create_password_subject}")
    private String createPasswordSubject;
    @Value("${create_password_endpoint}")
    private String createPasswordEndpoint;
    @Value("${create_password_template}")
    private String createPasswordTemplate;


    @Override
    public List<EmployeeDto> getEmployees(int pageNum, int pageSize,
                                          String roleId, String statusId, String searchPrompt) {
        Map<String, String> filters = new HashMap<>();

        if (!StringUtils.isBlank(roleId)) {
            filters.put("roleid", roleId);
        }

        if (!StringUtils.isBlank(statusId)) {
            filters.put("statusid", statusId);
        }

        if (!StringUtils.isBlank(searchPrompt)) {
            filters.put("search", searchPrompt);
        }

        return employeeDao.getAll(filters, pageNum, pageSize);
    }

    @Override
    public EmployeeDto getEmployeeById(Long userId) throws NotFoundException {
        return employeeDao.getById(userId);
    }

    @Override
    public List<EmployeeDto> getAvailableCouriers(Timestamp dateDelivery) throws SQLException {
        return employeeDao.getAvailableCouriers(dateDelivery);
    }

    @Override
    public void createEmployee(EmployeeDto employeeDto) throws IllegalArgumentException {
        Token token = new Token();
        token.setToken(UUID.randomUUID().toString());
        Long tokenId = tokenDao.insert(token);
        employeeDto.setTokenId(tokenId);
        employeeDao.insert(employeeDto);

        EmailComposer emailComposer = new EmailComposer(
                createPasswordUrl,
                createPasswordSubject,
                createPasswordEndpoint,
                createPasswordTemplate);
        User user = userService.getUserByEmail(employeeDto.getEmail());
        if (user == null) {
            throw new UserNotFoundException("Haven't found such employee email.");
        }
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, emailComposer));
    }

    @Override
    public void activateEmployee(User user) {
        user.setStatusId(Status.ACTIVATED.getValue());
        userDao.updateUserStatus(user);
    }

    @Override
    public int updateEmployee(EmployeeDto employeeDto) throws IllegalArgumentException {
        if (employeeDao.update(employeeDto) == 0) {
            throw new NotFoundException("Haven't found employee with such ID.");
        }

        return employeeDao.update(employeeDto);
    }

    @Override
    public int deleteEmployee(Long userId) {
        if (employeeDao.delete(userId) == 0) {
            throw new NotFoundException("Haven't found employee with such ID.");
        }

        return employeeDao.delete(userId);
    }
}
