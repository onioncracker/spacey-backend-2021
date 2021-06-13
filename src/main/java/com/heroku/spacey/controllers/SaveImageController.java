package com.heroku.spacey.controllers;

import com.heroku.spacey.services.AwsImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class SaveImageController {

    private final AwsImageService imageService;

    @PostMapping("/image/{id}")
    public HttpStatus addPhoto(@RequestPart(name = "image") MultipartFile img, Long id) throws IOException {
        imageService.save(img, id);
        return HttpStatus.OK;
    }

    @DeleteMapping("/image/{image}")
    public HttpStatus deletePhoto(@PathVariable String image) {
        imageService.delete(image);
        return HttpStatus.ACCEPTED;
    }

}
