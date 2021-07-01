package com.heroku.spacey.controllers;

import com.heroku.spacey.dto.color.ColorDto;
import com.heroku.spacey.services.ColorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/color")
public class ColorController {
    private final ColorService colorService;

    @GetMapping("/all")
    public List<ColorDto> getAllColors() {
        return colorService.getAllColors();
    }

    @GetMapping("/{id}")
    public ColorDto getColor(@PathVariable Long id) {
        return colorService.getById(id);
    }

    @PostMapping("/add")
    @Secured("ROLE_PRODUCT_MANAGER")
    public HttpStatus addColor(@RequestBody ColorDto colorDto) {
        colorService.addColor(colorDto);
        return HttpStatus.CREATED;
    }

    @PutMapping("/edit/{id}")
    @Secured("ROLE_PRODUCT_MANAGER")
    public HttpStatus editColor(@RequestBody ColorDto colorDto,
                                @PathVariable Long id) {
        ColorDto color = colorService.getById(id);
        colorService.updateColor(colorDto, color.getId());
        return HttpStatus.OK;
    }

    @DeleteMapping("/delete/{id}")
    @Secured("ROLE_PRODUCT_MANAGER")
    public HttpStatus deleteColor(@PathVariable Long id) {
        ColorDto color = colorService.getById(id);
        colorService.deleteColor(color.getId());
        return HttpStatus.ACCEPTED;
    }
}
