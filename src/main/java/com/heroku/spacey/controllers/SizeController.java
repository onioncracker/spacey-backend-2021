package com.heroku.spacey.controllers;

import com.heroku.spacey.dto.size.SizeDto;
import com.heroku.spacey.services.SizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/size")
public class SizeController {
    private final SizeService sizeService;

    @GetMapping("/all")
    public List<SizeDto> getAllSizes() {
        return sizeService.getAllSizes();
    }

    @GetMapping("/{id}")
    public SizeDto getSize(@PathVariable Long id) {
        return sizeService.getById(id);
    }

    @PostMapping("/add")
    public HttpStatus addSize(@RequestBody SizeDto sizeDto) {
        sizeService.addSize(sizeDto);
        return HttpStatus.CREATED;
    }

    @PutMapping("/edit/{id}")
    public HttpStatus editSize(@RequestBody SizeDto sizeDto,
                               @PathVariable Long id) {
        SizeDto size = sizeService.getById(id);
        sizeService.updateSize(sizeDto, size.getId());
        return HttpStatus.OK;
    }

    @DeleteMapping("/delete/{id}")
    public HttpStatus deleteSize(@PathVariable Long id) {
        SizeDto size = sizeService.getById(id);
        sizeService.deleteSize(size.getId());
        return HttpStatus.ACCEPTED;
    }
}
