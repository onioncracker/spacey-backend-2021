package com.heroku.spacey.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.net.URI;
import java.net.URISyntaxException;

@Data
@AllArgsConstructor
public class EmailComposer {
    private String subject;
    private String endpoint;
    private String url;

    public String composeUri(String token) throws URISyntaxException {
        URI uri = new URI(url + "/api/v1/" + endpoint + token);
        return uri.toString();
    }
}
