package com.jeanbarcellos.project101.presentation.web.controllers;

import static com.jeanbarcellos.core.constants.ApiConstants.BEARER_KEY;
import static com.jeanbarcellos.core.constants.ApiConstants.MEDIA_TYPE_APPLICATION_JSON;
import static com.jeanbarcellos.core.constants.ApiConstants.STATUS_200_DESCRIPTION;
import static com.jeanbarcellos.core.constants.ApiConstants.STATUS_201_DESCRIPTION;
import static com.jeanbarcellos.core.constants.ApiConstants.STATUS_400_DESCRIPTION;
import static com.jeanbarcellos.core.constants.ApiConstants.STATUS_401_DESCRIPTION;
import static com.jeanbarcellos.core.constants.ApiConstants.STATUS_403_DESCRIPTION;
import static com.jeanbarcellos.core.constants.ApiConstants.STATUS_404_DESCRIPTION;
import static com.jeanbarcellos.core.constants.ApiConstants.STATUS_500_DESCRIPTION;

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

import com.jeanbarcellos.core.constants.ApiConstants;
import com.jeanbarcellos.core.dto.ErrorResponse;
import com.jeanbarcellos.core.dto.PageRequest;
import com.jeanbarcellos.core.dto.SuccessResponse;
import com.jeanbarcellos.core.web.ControllerBase;
import com.jeanbarcellos.project101.application.dtos.ProductRequest;
import com.jeanbarcellos.project101.application.dtos.ProductResponse;
import com.jeanbarcellos.project101.application.services.ProductService;
import com.jeanbarcellos.project101.infra.configurations.Roles;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
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
@SecurityRequirement(name = BEARER_KEY)
public class ProductController extends ControllerBase {

    @Autowired
    private ProductService productService;

    @GetMapping("")
    @Operation(summary = "Listar produtos", description = "Lista todas os produtos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = STATUS_200_DESCRIPTION, headers = {
                    @Header(name = ApiConstants.PAGINATION_PAGE_CURRENT_KEY, description = ApiConstants.PAGINATION_PAGE_CURRENT_DESCRIPTION, schema = @Schema(type = ApiConstants.PAGINATION_HEADER_SCHEMA)),
                    @Header(name = ApiConstants.PAGINATION_PAGE_SIZE_KEY, description = ApiConstants.PAGINATION_PAGE_SIZE_DESCRIPTION, schema = @Schema(type = ApiConstants.PAGINATION_HEADER_SCHEMA)),
                    @Header(name = ApiConstants.PAGINATION_TOTAL_PAGES_KEY, description = ApiConstants.PAGINATION_TOTAL_PAGES_DESCRIPTION, schema = @Schema(type = ApiConstants.PAGINATION_HEADER_SCHEMA)),
                    @Header(name = ApiConstants.PAGINATION_TOTAL_COUNT_KEY, description = ApiConstants.PAGINATION_TOTAL_COUNT_DESCRIPTION, schema = @Schema(type = ApiConstants.PAGINATION_HEADER_SCHEMA))
            }, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = ProductResponse.class)))),
            @ApiResponse(responseCode = "401", description = STATUS_401_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = STATUS_403_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = STATUS_500_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<List<ProductResponse>> getAll(
            @RequestParam(value = ApiConstants.PARAM_PAGE_CURRENT, defaultValue = ApiConstants.PAGE_CURRENT_DEFAULT_STRING, required = false) Integer page,
            @RequestParam(value = ApiConstants.PARAM_PAGE_SIZE, defaultValue = ApiConstants.PAGE_SIZE_DEFAULT_STRING, required = false) Integer size,
            @RequestParam(value = ApiConstants.PARAM_SORT, defaultValue = ApiConstants.PARAM_SORT_CREATED_DESC, required = false) String sort) {
        return this.paginatedResponse(this.productService.getAll(PageRequest.of(page, size, sort)));
    }

    @GetMapping("/by-category/{categoryId}")
    @Operation(summary = "Listar produtos de uma determinada categoria", description = "Lista todas os produtos de uma categoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = STATUS_200_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = ProductResponse.class)))),
            @ApiResponse(responseCode = "401", description = STATUS_401_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = STATUS_403_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = STATUS_500_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<List<ProductResponse>> getByCategory(@PathVariable UUID categoryId) {
        return ResponseEntity.ok(this.productService.getByCategory(categoryId));
    }

    @GetMapping(PATH_SHOW)
    @Operation(summary = "Exibir produto", description = "Exibe detalhes um produto a partir de um ID informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = STATUS_200_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "401", description = STATUS_401_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = STATUS_403_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = STATUS_404_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = STATUS_500_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<ProductResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(this.productService.getById(id));
    }

    @PostMapping("")
    @Operation(summary = "Incluir produto", description = "Inclui um novo produto")
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
    @Operation(summary = "Alterar produto", description = "Altera uma produto existente a partit do seu ID")
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
    @Operation(summary = "Ativar produto", description = "Ativa um produto atualmente inativado")
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
    @Operation(summary = "Inativar produto", description = "Inativa um produto atualmente ativado")
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
