package com.heroku.spacey.controllers;

import com.heroku.spacey.services.MaterialService;
import com.heroku.spacey.dto.material.MaterialDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/material")
public class MaterialController {
    private final MaterialService materialService;

    @GetMapping("/all")
    public ResponseEntity<List<MaterialDto>> getAllMaterials() {
        List<MaterialDto> materials = materialService.getAllMaterials();
        return new ResponseEntity<>(materials, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaterialDto> getMaterial(@PathVariable Long id) {
        MaterialDto material = materialService.getById(id);
        return new ResponseEntity<>(material, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addMaterial(@RequestBody MaterialDto materialDto) {
        materialService.addMaterial(materialDto);
        return new ResponseEntity<>("Add material successfully", HttpStatus.CREATED);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<MaterialDto> editMaterial(@RequestBody MaterialDto materialDto,
                                                    @PathVariable Long id) {
        MaterialDto material = materialService.getById(id);
        materialService.updateMaterial(materialDto, material.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MaterialDto> deleteMaterial(@PathVariable Long id) {
        MaterialDto material = materialService.getById(id);
        materialService.deleteMaterial(material.getId());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
