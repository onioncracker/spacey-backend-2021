package com.heroku.spacey.utils.convertors;

import com.heroku.spacey.dto.user.UserRegisterDto;
import com.heroku.spacey.entity.LoginInfo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class LoginInfoConvertor {
    private final ModelMapper mapper = new ModelMapper();

    public LoginInfo adapt(UserRegisterDto userRegisterDto) {
        mapper.typeMap(UserRegisterDto.class, LoginInfo.class);
        return mapper.map(userRegisterDto, LoginInfo.class);
    }
}
