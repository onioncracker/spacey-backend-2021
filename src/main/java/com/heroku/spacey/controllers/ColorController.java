package com.heroku.spacey.controllers;

import com.heroku.spacey.dto.color.ColorDto;
import com.heroku.spacey.services.ColorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/color")
public class ColorController {
    private final ColorService colorService;

    @GetMapping("/all")
    public ResponseEntity<List<ColorDto>> getAllColors() {
        List<ColorDto> colors = colorService.getAllColors();
        return new ResponseEntity<>(colors, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ColorDto> getColor(@PathVariable Long id) {
        ColorDto color = colorService.getById(id);
        return new ResponseEntity<>(color, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addColor(@RequestBody ColorDto colorDto) {
        colorService.addColor(colorDto);
        return new ResponseEntity<>("Add color successfully", HttpStatus.CREATED);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<ColorDto> editColor(@RequestBody ColorDto colorDto,
                                              @PathVariable Long id) {
        ColorDto color = colorService.getById(id);
        colorService.updateColor(colorDto, color.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ColorDto> deleteColor(@PathVariable Long id) {
        ColorDto color = colorService.getById(id);
        colorService.deleteColor(color.getId());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
