package com.heroku.spacey.utils.convertors;

import com.heroku.spacey.dto.registration.TokenDto;
import com.heroku.spacey.entity.Token;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TokenConvertor {
    private final ModelMapper mapper = new ModelMapper();

    public Token adapt(TokenDto source) {
        mapper.typeMap(TokenDto.class, Token.class);
        return mapper.map(source, Token.class);
    }

    public TokenDto adapt(Token source) {
        mapper.typeMap(Token.class, TokenDto.class);
        return mapper.map(source, TokenDto.class);
    }
}
