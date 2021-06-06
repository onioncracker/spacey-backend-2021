package com.heroku.spacey.controllers;

import com.heroku.spacey.services.AwsImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;

@RestController
@RequiredArgsConstructor
public class SaveImageController {

    private final AwsImageService imageService;

    @PostMapping("/image")
    public ResponseEntity<URL> addPhoto(@RequestPart(name = "image") MultipartFile img) throws IOException {
        URL imageUrl = imageService.save(img);
        return new ResponseEntity<>(imageUrl, HttpStatus.OK);
    }

    @DeleteMapping("/image/{image}")
    public HttpStatus deletePhoto(@PathVariable String image) {
        imageService.delete(image);
        return HttpStatus.ACCEPTED;
    }

}
