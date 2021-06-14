package com.heroku.spacey.controllers;

import com.heroku.spacey.services.MaterialService;
import com.heroku.spacey.dto.material.MaterialDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/material")
public class MaterialController {
    private final MaterialService materialService;

    @GetMapping("/all")
    public List<MaterialDto> getAllMaterials() {
        return materialService.getAllMaterials();
    }

    @GetMapping("/{id}")
    public MaterialDto getMaterial(@PathVariable Long id) {
        return materialService.getById(id);
    }

    @PostMapping("/add")
    public HttpStatus addMaterial(@RequestBody MaterialDto materialDto) {
        materialService.addMaterial(materialDto);
        return HttpStatus.CREATED;
    }

    @PutMapping("/edit/{id}")
    public HttpStatus editMaterial(@RequestBody MaterialDto materialDto,
                                   @PathVariable Long id) {
        MaterialDto material = materialService.getById(id);
        materialService.updateMaterial(materialDto, material.getId());
        return HttpStatus.OK;
    }

    @DeleteMapping("/delete/{id}")
    public HttpStatus deleteMaterial(@PathVariable Long id) {
        MaterialDto material = materialService.getById(id);
        materialService.deleteMaterial(material.getId());
        return HttpStatus.ACCEPTED;
    }
}
