package com.jeanbarcellos.demo.web.controllers;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import com.jeanbarcellos.demo.application.dtos.RoleCollectionResponse;
import com.jeanbarcellos.demo.application.dtos.RoleRequest;
import com.jeanbarcellos.demo.application.dtos.RoleResponse;
import com.jeanbarcellos.demo.application.services.RoleService;
import com.jeanbarcellos.demo.core.dtos.SuccessResponse;
import com.jeanbarcellos.demo.core.web.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RoleController extends Controller {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResponseEntity<List<RoleCollectionResponse>> index() {
        List<RoleCollectionResponse> response = roleService.getAll();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponse> findById(@PathVariable UUID id) {
        RoleResponse response = roleService.getById(id);

        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<RoleResponse> insert(@RequestBody @Valid RoleRequest request) {
        RoleResponse response = roleService.insert(request);

        return ResponseEntity.created(this.createUriLocation("/{id}", response.getId())).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleResponse> update(@RequestBody @Valid RoleRequest request, @PathVariable UUID id) {
        RoleResponse response = roleService.update(id, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public SuccessResponse delete(@PathVariable UUID id) {
        return roleService.delete(id);
    }

}
