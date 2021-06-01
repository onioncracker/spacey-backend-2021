package com.heroku.spacey.controllers;

import com.heroku.spacey.contracts.MaterialService;
import com.heroku.spacey.dto.material.MaterialDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MaterialController {
    private final MaterialService materialService;

    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @GetMapping("/api/material/{id}")
    public ResponseEntity<MaterialDto> getMaterial(@PathVariable int id) {
        try {
            var material = materialService.getById(id);
            if (material == null) {
                return new ResponseEntity("material not found by id", HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(material);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/api/material/add")
    public ResponseEntity addMaterial(@RequestBody MaterialDto materialDto) {
        try {
            materialService.addMaterial(materialDto);
            return new ResponseEntity("added material successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/api/material/edit")
    public ResponseEntity<String> editMaterial(@RequestBody MaterialDto materialDto) {
        try {
            materialService.updateMaterial(materialDto);
            return ResponseEntity.ok("successfully");
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/api/material/delete/{id}")
    public ResponseEntity<String> deleteMaterial(@PathVariable int id) {
        try {
            materialService.deleteMaterial(id);
            return ResponseEntity.ok("successfully");
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
