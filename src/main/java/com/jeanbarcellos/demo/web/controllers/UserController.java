package com.jeanbarcellos.demo.web.controllers;

import static com.jeanbarcellos.demo.config.Roles.HAS_ROLE_ROOT;
import static com.jeanbarcellos.demo.config.constants.APIConstants.BEARER_KEY;
import static com.jeanbarcellos.demo.config.constants.APIConstants.ERROR_200_DESCRIPTION;
import static com.jeanbarcellos.demo.config.constants.APIConstants.ERROR_201_DESCRIPTION;
import static com.jeanbarcellos.demo.config.constants.APIConstants.ERROR_400_DESCRIPTION;
import static com.jeanbarcellos.demo.config.constants.APIConstants.ERROR_401_DESCRIPTION;
import static com.jeanbarcellos.demo.config.constants.APIConstants.ERROR_403_DESCRIPTION;
import static com.jeanbarcellos.demo.config.constants.APIConstants.ERROR_404_DESCRIPTION;
import static com.jeanbarcellos.demo.config.constants.APIConstants.ERROR_500_DESCRIPTION;
import static com.jeanbarcellos.demo.config.constants.APIConstants.MEDIA_TYPE_APPLICATION_JSON;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.jeanbarcellos.demo.application.dtos.UserFullResponse;
import com.jeanbarcellos.demo.application.dtos.UserRequest;
import com.jeanbarcellos.demo.application.dtos.UserResponse;
import com.jeanbarcellos.demo.application.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/users")
@PreAuthorize(HAS_ROLE_ROOT)
@Tag(name = "Usuários", description = "Manutenção de usuários")
public class UserController extends ControllerBase {

    @Autowired
    private UserService userService;

    @GetMapping
    @Operation(summary = "Listar usuários", description = "Lista todos os usuários", security = {
            @SecurityRequirement(name = BEARER_KEY) })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = ERROR_200_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = UserResponse.class)))),
            @ApiResponse(responseCode = "400", description = ERROR_400_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = ERROR_403_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = ERROR_404_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = ERROR_500_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<List<UserResponse>> showAll() {
        List<UserResponse> list = userService.getAll();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Exibir usuário", description = "Exibe detalhes um usuário a partir de um ID informado", security = {
            @SecurityRequirement(name = BEARER_KEY) })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = ERROR_200_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = UserFullResponse.class))),
            @ApiResponse(responseCode = "401", description = ERROR_401_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = ERROR_403_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = ERROR_404_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = ERROR_500_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<UserFullResponse> show(@PathVariable UUID id) {
        UserFullResponse response = userService.getById(id);

        return ResponseEntity.ok(response);
    }

    @PostMapping("")
    @Operation(summary = "Incluir usuário", description = "Inclui um novo usuário", security = {
            @SecurityRequirement(name = BEARER_KEY) })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = ERROR_201_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = UserFullResponse.class))),
            @ApiResponse(responseCode = "400", description = ERROR_400_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = ERROR_401_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = ERROR_403_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = ERROR_500_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<UserFullResponse> insert(@RequestBody @Valid UserRequest request) {
        UserFullResponse response = userService.insert(request);

        return ResponseEntity.created(this.createUriLocation("/{id}", response.getId())).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Alterar usuário", description = "Altera um novo usuário existente", security = {
            @SecurityRequirement(name = BEARER_KEY) })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = ERROR_200_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = UserFullResponse.class))),
            @ApiResponse(responseCode = "400", description = ERROR_400_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = ERROR_401_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = ERROR_403_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = ERROR_404_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = ERROR_500_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<UserFullResponse> update(@RequestBody @Valid UserRequest request, @PathVariable UUID id) {
        UserFullResponse response = userService.update(id, request);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/activate")
    @Operation(summary = "Ativar usuário", description = "Ativa um usuário atualmente inativado", security = {
            @SecurityRequirement(name = BEARER_KEY) })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = ERROR_200_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "400", description = ERROR_400_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = ERROR_401_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = ERROR_403_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = ERROR_404_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = ERROR_500_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<SuccessResponse> activate(@PathVariable UUID id) {
        SuccessResponse response = userService.activate(id);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/inactivate")
    @Operation(summary = "Inativar usuário", description = "Inativa um usuário atualmente ativado", security = {
            @SecurityRequirement(name = BEARER_KEY) })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = ERROR_200_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "400", description = ERROR_400_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = ERROR_401_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = ERROR_403_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = ERROR_404_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = ERROR_500_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<SuccessResponse> inactivate(@PathVariable UUID id) {
        SuccessResponse response = userService.inactivate(id);

        return ResponseEntity.ok(response);
    }

}