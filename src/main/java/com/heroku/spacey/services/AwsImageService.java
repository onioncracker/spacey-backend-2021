package com.heroku.spacey.services;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public interface AwsImageService {
    void save(MultipartFile img, Long productId) throws IOException;

    String getUrl(String fileName);

    void delete(String fileName);
}
