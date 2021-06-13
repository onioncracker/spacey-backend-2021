package com.heroku.spacey.utils.convertors;

import com.heroku.spacey.dto.user.UserRegisterDto;
import com.heroku.spacey.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserConvertor {
    private final ModelMapper mapper = new ModelMapper();

    public User adapt(UserRegisterDto userRegisterDto) {
        mapper.typeMap(UserRegisterDto.class, User.class);
        return mapper.map(userRegisterDto, User.class);
    }
}
