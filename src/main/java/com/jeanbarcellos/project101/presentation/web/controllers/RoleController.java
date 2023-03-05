package com.jeanbarcellos.project101.presentation.web.controllers;

import static com.jeanbarcellos.project101.infra.configurations.Roles.HAS_ROLE_ROOT;
import static com.jeanbarcellos.project101.infra.configurations.constants.APIConstants.BEARER_KEY;
import static com.jeanbarcellos.project101.infra.configurations.constants.APIConstants.ERROR_200_DESCRIPTION;
import static com.jeanbarcellos.project101.infra.configurations.constants.APIConstants.ERROR_201_DESCRIPTION;
import static com.jeanbarcellos.project101.infra.configurations.constants.APIConstants.ERROR_400_DESCRIPTION;
import static com.jeanbarcellos.project101.infra.configurations.constants.APIConstants.ERROR_401_DESCRIPTION;
import static com.jeanbarcellos.project101.infra.configurations.constants.APIConstants.ERROR_403_DESCRIPTION;
import static com.jeanbarcellos.project101.infra.configurations.constants.APIConstants.ERROR_404_DESCRIPTION;
import static com.jeanbarcellos.project101.infra.configurations.constants.APIConstants.ERROR_500_DESCRIPTION;
import static com.jeanbarcellos.project101.infra.configurations.constants.APIConstants.MEDIA_TYPE_APPLICATION_JSON;

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

import com.jeanbarcellos.core.dto.ErrorResponse;
import com.jeanbarcellos.core.dto.SuccessResponse;
import com.jeanbarcellos.core.web.ControllerBase;
import com.jeanbarcellos.project101.application.dtos.RoleResponse;
import com.jeanbarcellos.project101.application.dtos.RoleRequest;
import com.jeanbarcellos.project101.application.dtos.RoleSimpleResponse;
import com.jeanbarcellos.project101.application.services.RoleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/roles")
@PreAuthorize(HAS_ROLE_ROOT)
@Tag(name = "Funções", description = "Manutenção de funções")
public class RoleController extends ControllerBase {

    @Autowired
    private RoleService roleService;

    @GetMapping
    @Operation(summary = "Listar funções", description = "Lista todas as funções", security = {
            @SecurityRequirement(name = BEARER_KEY) })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = ERROR_200_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = RoleSimpleResponse.class)))),
            @ApiResponse(responseCode = "400", description = ERROR_400_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = ERROR_401_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = ERROR_403_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = ERROR_500_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<List<RoleSimpleResponse>> showAll() {
        List<RoleSimpleResponse> response = this.roleService.getAll();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Exibir função", description = "Exibe detalhes uma função a partir de um ID informado", security = {
            @SecurityRequirement(name = BEARER_KEY) })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = ERROR_200_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = RoleResponse.class))),
            @ApiResponse(responseCode = "401", description = ERROR_401_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = ERROR_403_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = ERROR_404_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = ERROR_500_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<RoleResponse> show(@PathVariable UUID id) {
        RoleResponse response = this.roleService.getById(id);

        return ResponseEntity.ok(response);
    }

    @PostMapping("")
    @Operation(summary = "Incluir função", description = "Inclui uma nova função", security = {
            @SecurityRequirement(name = BEARER_KEY) })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = ERROR_201_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = RoleResponse.class))),
            @ApiResponse(responseCode = "400", description = ERROR_400_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = ERROR_401_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = ERROR_403_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = ERROR_500_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<RoleResponse> insert(@RequestBody @Valid RoleRequest request) {
        RoleResponse response = this.roleService.insert(request);

        return ResponseEntity.created(this.createUriLocation("/{id}", response.getId())).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Alterar função", description = "Altera uma nova função existente", security = {
            @SecurityRequirement(name = BEARER_KEY) })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = ERROR_200_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = RoleResponse.class))),
            @ApiResponse(responseCode = "400", description = ERROR_400_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = ERROR_401_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = ERROR_403_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = ERROR_404_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = ERROR_500_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<RoleResponse> update(@RequestBody @Valid RoleRequest request, @PathVariable UUID id) {
        RoleResponse response = this.roleService.update(id, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir função", description = "Apaga uma nova função existente", security = {
            @SecurityRequirement(name = BEARER_KEY) })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = ERROR_200_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = RoleResponse.class))),
            @ApiResponse(responseCode = "401", description = ERROR_401_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = ERROR_403_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = ERROR_404_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = ERROR_500_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class)))
    })
    public SuccessResponse delete(@PathVariable UUID id) {
        return this.roleService.delete(id);
    }

}
