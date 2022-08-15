package com.jeanbarcellos.demo.web.controllers;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import com.jeanbarcellos.core.dtos.SuccessResponse;
import com.jeanbarcellos.core.web.ControllerBase;
import com.jeanbarcellos.demo.application.dtos.CategoryRequest;
import com.jeanbarcellos.demo.application.dtos.CategoryResponse;
import com.jeanbarcellos.demo.application.services.CategoryService;
import com.jeanbarcellos.demo.config.Roles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
@PreAuthorize("hasRole('" + Roles.DEFAULT + "')")
public class CategoryController extends ControllerBase {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> findAll() {
        List<CategoryResponse> response = categoryService.getAll();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> show(@PathVariable UUID id) {
        CategoryResponse response = categoryService.getById(id);

        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<CategoryResponse> insert(@RequestBody @Valid CategoryRequest request) {
        CategoryResponse response = categoryService.insert(request);

        return ResponseEntity.created(this.createUriLocation("/{id}", response.getId())).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> update(@RequestBody @Valid CategoryRequest request,
            @PathVariable UUID id) {
        CategoryResponse response = categoryService.update(id, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public SuccessResponse delete(@PathVariable UUID id) {
        return categoryService.delete(id);
    }

}
