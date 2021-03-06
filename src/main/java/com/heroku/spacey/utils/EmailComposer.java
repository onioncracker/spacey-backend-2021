package com.heroku.spacey.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.util.UriComponentsBuilder;

@Data
@AllArgsConstructor
public class EmailComposer {
    private String url;
    private String subject;
    private String endpoint;
    private String template;

    public String composeUri(String token) {
        return UriComponentsBuilder.fromUriString(url)
                .pathSegment(endpoint)
                .queryParam("token", token)
                .build().toString();
    }
}
