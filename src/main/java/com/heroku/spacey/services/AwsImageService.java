package com.heroku.spacey.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;

public interface AwsImageService {
    URL save(MultipartFile img) throws IOException;

    URL getUrl(String fileName);

    void delete(String fileName);
}
