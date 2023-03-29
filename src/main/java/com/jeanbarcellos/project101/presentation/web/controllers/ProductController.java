package com.jeanbarcellos.project101.presentation.web.controllers;

import static com.jeanbarcellos.project101.infra.configurations.constants.APIConstants.BEARER_KEY;
import static com.jeanbarcellos.project101.infra.configurations.constants.APIConstants.MEDIA_TYPE_APPLICATION_JSON;
import static com.jeanbarcellos.project101.infra.configurations.constants.APIConstants.STATUS_200_DESCRIPTION;
import static com.jeanbarcellos.project101.infra.configurations.constants.APIConstants.STATUS_201_DESCRIPTION;
import static com.jeanbarcellos.project101.infra.configurations.constants.APIConstants.STATUS_400_DESCRIPTION;
import static com.jeanbarcellos.project101.infra.configurations.constants.APIConstants.STATUS_401_DESCRIPTION;
import static com.jeanbarcellos.project101.infra.configurations.constants.APIConstants.STATUS_403_DESCRIPTION;
import static com.jeanbarcellos.project101.infra.configurations.constants.APIConstants.STATUS_404_DESCRIPTION;
import static com.jeanbarcellos.project101.infra.configurations.constants.APIConstants.STATUS_500_DESCRIPTION;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jeanbarcellos.core.dto.ErrorResponse;
import com.jeanbarcellos.core.dto.PageSortRequest;
import com.jeanbarcellos.core.dto.SuccessResponse;
import com.jeanbarcellos.core.web.ControllerBase;
import com.jeanbarcellos.project101.application.dtos.ProductRequest;
import com.jeanbarcellos.project101.application.dtos.ProductResponse;
import com.jeanbarcellos.project101.application.services.ProductService;
import com.jeanbarcellos.project101.infra.configurations.Roles;
import com.jeanbarcellos.project101.infra.configurations.constants.APIConstants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/products")
@PreAuthorize(Roles.HAS_ROLE_DEFAULT)
@Tag(name = "Produtos", description = "Manutenção de produtos")
public class ProductController extends ControllerBase {

    @Autowired
    private ProductService productService;

    @GetMapping("")
    @Operation(summary = "Listar produtos", description = "Lista todas os produtos", security = {
            @SecurityRequirement(name = BEARER_KEY) })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = STATUS_200_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = ProductResponse.class)))),
            @ApiResponse(responseCode = "401", description = STATUS_401_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = STATUS_403_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = STATUS_500_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<List<ProductResponse>> findAll(
            @RequestParam(value = APIConstants.PARAM_PAGE, defaultValue = APIConstants.PARAM_PAGE_DEFAULT, required = false) Integer page,
            @RequestParam(value = APIConstants.PARAM_PAGE_SIZE, defaultValue = APIConstants.PARAM_PAGE_SIZE_DEFAULT, required = false) Integer size,
            @RequestParam(value = APIConstants.PARAM_SORT, defaultValue = APIConstants.PARAM_SORT_DEFAULT, required = false) String sort) {
        return this.paginatedResponse(this.productService.getAll(PageSortRequest.of(page, size, sort)));
    }

    @GetMapping("/by-category/{categoryId}")
    @Operation(summary = "Listar produtos de uma determinada categoria", description = "Lista todas os produtos de uma categoria", security = {
            @SecurityRequirement(name = BEARER_KEY) })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = STATUS_200_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = ProductResponse.class)))),
            @ApiResponse(responseCode = "401", description = STATUS_401_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = STATUS_403_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = STATUS_500_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<List<ProductResponse>> findByCategory(@PathVariable UUID categoryId) {
        return ResponseEntity.ok(this.productService.getByCategory(categoryId));
    }

    @GetMapping(PATH_SHOW)
    @Operation(summary = "Exibir produto", description = "Exibe detalhes um produto a partir de um ID informado", security = {
            @SecurityRequirement(name = BEARER_KEY) })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = STATUS_200_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "401", description = STATUS_401_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = STATUS_403_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = STATUS_404_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = STATUS_500_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<ProductResponse> show(@PathVariable UUID id) {
        return ResponseEntity.ok(this.productService.getById(id));
    }

    @PostMapping("")
    @Operation(summary = "Incluir produto", description = "Inclui um novo produto", security = {
            @SecurityRequirement(name = BEARER_KEY) })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = STATUS_201_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "400", description = STATUS_400_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = STATUS_401_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = STATUS_403_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = STATUS_500_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<ProductResponse> insert(@RequestBody ProductRequest request) {
        var response = this.productService.insert(request);

        return ResponseEntity.created(this.createUriLocation(PATH_SHOW, response.getId())).body(response);
    }

    @PutMapping(PATH_SHOW)
    @Operation(summary = "Alterar produto", description = "Altera uma produto existente a partit do seu ID", security = {
            @SecurityRequirement(name = BEARER_KEY) })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = STATUS_200_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "400", description = STATUS_400_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = STATUS_401_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = STATUS_403_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = STATUS_404_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = STATUS_500_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<ProductResponse> update(@PathVariable UUID id, @RequestBody ProductRequest request) {
        return ResponseEntity.ok(this.productService.update(request.setId(id)));
    }

    @PutMapping(PATH_SHOW + "/activate")
    @Operation(summary = "Ativar produto", description = "Ativa um produto atualmente inativado", security = {
            @SecurityRequirement(name = BEARER_KEY) })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = STATUS_200_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "400", description = STATUS_400_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = STATUS_401_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = STATUS_403_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = STATUS_404_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = STATUS_500_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<SuccessResponse> activate(@PathVariable UUID id) {
        return ResponseEntity.ok(this.productService.activate(id));
    }

    @PutMapping(PATH_SHOW + "/inactivate")
    @Operation(summary = "Inativar produto", description = "Inativa um produto atualmente ativado", security = {
            @SecurityRequirement(name = BEARER_KEY) })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = STATUS_200_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "400", description = STATUS_400_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = STATUS_401_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = STATUS_403_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = STATUS_404_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = STATUS_500_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<SuccessResponse> inactivate(@PathVariable UUID id) {
        return ResponseEntity.ok(this.productService.inactivate(id));
    }

}
