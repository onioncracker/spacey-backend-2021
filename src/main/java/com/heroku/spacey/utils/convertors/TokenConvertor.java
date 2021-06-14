package com.heroku.spacey.utils.convertors;

import com.heroku.spacey.dto.registration.VerificationTokenDto;
import com.heroku.spacey.entity.VerificationToken;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TokenConvertor {
    private final ModelMapper mapper = new ModelMapper();

    public VerificationToken adapt(VerificationTokenDto source) {
        mapper.typeMap(VerificationTokenDto.class, VerificationToken.class);
        return mapper.map(source, VerificationToken.class);
    }

    public VerificationTokenDto adapt(VerificationToken source) {
        mapper.typeMap(VerificationToken.class, VerificationTokenDto.class);
        return mapper.map(source, VerificationTokenDto.class);
    }
}
