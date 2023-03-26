package com.jeanbarcellos.project101.presentation.web.controllers;

import static com.jeanbarcellos.project101.infra.configurations.Roles.HAS_ROLE_DEFAULT;
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
import com.jeanbarcellos.project101.application.dtos.CategoryRequest;
import com.jeanbarcellos.project101.application.dtos.CategoryResponse;
import com.jeanbarcellos.project101.application.services.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/categories")
@PreAuthorize(HAS_ROLE_DEFAULT)
@Tag(name = "Categorias", description = "Manutenção de categorias")
public class CategoryController extends ControllerBase {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    @Operation(summary = "Listar categorias", description = "Lista todas as categorias", security = {
            @SecurityRequirement(name = BEARER_KEY) })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = ERROR_200_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = CategoryResponse.class)))),
            @ApiResponse(responseCode = "401", description = ERROR_401_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = ERROR_403_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = ERROR_500_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<List<CategoryResponse>> findAll() {
        return ResponseEntity.ok(this.categoryService.getAll());
    }

    @GetMapping(PATH_SHOW)
    @Operation(summary = "Exibir categoria", description = "Exibe detalhes uma categoria a partir de um ID informado", security = {
            @SecurityRequirement(name = BEARER_KEY) })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = ERROR_200_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = CategoryResponse.class))),
            @ApiResponse(responseCode = "401", description = ERROR_401_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = ERROR_403_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = ERROR_404_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = ERROR_500_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<CategoryResponse> show(@PathVariable UUID id) {
        return ResponseEntity.ok(this.categoryService.getById(id));
    }

    @PostMapping("")
    @Operation(summary = "Incluir categoria", description = "Inclui uma nova categoria", security = {
            @SecurityRequirement(name = BEARER_KEY) })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = ERROR_201_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = CategoryResponse.class))),
            @ApiResponse(responseCode = "400", description = ERROR_400_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = ERROR_401_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = ERROR_403_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = ERROR_500_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<CategoryResponse> insert(@RequestBody @Valid CategoryRequest request) {
        var response = this.categoryService.insert(request);

        return ResponseEntity.created(this.createUriLocation(PATH_SHOW, response.getId())).body(response);
    }

    @PutMapping(PATH_SHOW)
    @Operation(summary = "Alterar categoria", description = "Altera uma nova categoria existente", security = {
            @SecurityRequirement(name = BEARER_KEY) })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = ERROR_200_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = CategoryResponse.class))),
            @ApiResponse(responseCode = "400", description = ERROR_400_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = ERROR_401_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = ERROR_403_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = ERROR_404_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = ERROR_500_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<CategoryResponse> update(@PathVariable UUID id, @RequestBody @Valid CategoryRequest request) {
        return ResponseEntity.ok(this.categoryService.update(request.setId(id)));
    }

    @DeleteMapping(PATH_SHOW)
    @Operation(summary = "Excluir categoria", description = "Exclui uma nova categoria existente", security = {
            @SecurityRequirement(name = BEARER_KEY) })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = ERROR_200_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "401", description = ERROR_401_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = ERROR_403_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = ERROR_404_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = ERROR_500_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<SuccessResponse> delete(@PathVariable UUID id) {
        return ResponseEntity.ok(this.categoryService.delete(id));
    }

}
