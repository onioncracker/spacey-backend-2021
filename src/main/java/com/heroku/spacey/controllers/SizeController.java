package com.heroku.spacey.controllers;

import com.heroku.spacey.dto.category.CategoryDto;
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
    public ResponseEntity<List<SizeDto>> getAllSizes() {
        List<SizeDto> sizes = sizeService.getAllSizes();
        return new ResponseEntity<>(sizes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SizeDto> getSize(@PathVariable Long id) {
        SizeDto size = sizeService.getById(id);
        return new ResponseEntity<>(size, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addSize(@RequestBody SizeDto sizeDto) {
        sizeService.addSize(sizeDto);
        return new ResponseEntity<>("Add size successfully", HttpStatus.CREATED);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<SizeDto> editSize(@RequestBody SizeDto sizeDto,
                                            @PathVariable Long id) {
        SizeDto size = sizeService.getById(id);
        sizeService.updateSize(sizeDto, size.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<SizeDto> deleteSize(@PathVariable Long id) {
        SizeDto size = sizeService.getById(id);
        sizeService.deleteSize(size.getId());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
