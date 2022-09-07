package com.jeanbarcellos.demo.web.controllers;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

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

import com.jeanbarcellos.core.dtos.SuccessResponse;
import com.jeanbarcellos.core.web.ControllerBase;
import com.jeanbarcellos.demo.application.dtos.RoleCollectionResponse;
import com.jeanbarcellos.demo.application.dtos.RoleRequest;
import com.jeanbarcellos.demo.application.dtos.RoleResponse;
import com.jeanbarcellos.demo.application.services.RoleService;
import com.jeanbarcellos.demo.config.Roles;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/roles")
@PreAuthorize("hasRole('" + Roles.ROOT + "')")
@Tag(name = "Funções", description = "Manuteção de funções")
public class RoleController extends ControllerBase {

    @Autowired
    private RoleService roleService;

    @GetMapping
    @Operation(summary = "Listar funções", description = "Lista todas as funções")
    public ResponseEntity<List<RoleCollectionResponse>> showAll() {
        List<RoleCollectionResponse> response = roleService.getAll();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Exibir função", description = "Exibe detalhes uma função a partir de um ID informado")
    public ResponseEntity<RoleResponse> show(@PathVariable UUID id) {
        RoleResponse response = roleService.getById(id);

        return ResponseEntity.ok(response);
    }

    @PostMapping("")
    @Operation(summary = "Incluir função", description = "Inclui uma nova função")
    public ResponseEntity<RoleResponse> insert(@RequestBody @Valid RoleRequest request) {
        RoleResponse response = roleService.insert(request);

        return ResponseEntity.created(this.createUriLocation("/{id}", response.getId())).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Alterar função", description = "Altera uma nova função existente")
    public ResponseEntity<RoleResponse> update(@RequestBody @Valid RoleRequest request, @PathVariable UUID id) {
        RoleResponse response = roleService.update(id, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir função", description = "Apaga uma nova função existente")
    public SuccessResponse delete(@PathVariable UUID id) {
        return roleService.delete(id);
    }

}
