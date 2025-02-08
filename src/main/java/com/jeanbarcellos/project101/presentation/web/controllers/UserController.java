package com.jeanbarcellos.project101.presentation.web.controllers;

import static com.jeanbarcellos.project101.infra.configurations.Roles.HAS_ROLE_ROOT;
import static com.jeanbarcellos.project101.infra.constants.APIConstants.MEDIA_TYPE_APPLICATION_JSON;
import static com.jeanbarcellos.project101.infra.constants.APIConstants.PAGINATION_DESCRIPTION_CURRENT_PAGE;
import static com.jeanbarcellos.project101.infra.constants.APIConstants.PAGINATION_DESCRIPTION_PAGES;
import static com.jeanbarcellos.project101.infra.constants.APIConstants.PAGINATION_DESCRIPTION_PER_PAGE;
import static com.jeanbarcellos.project101.infra.constants.APIConstants.PAGINATION_DESCRIPTION_TOTAL;
import static com.jeanbarcellos.project101.infra.constants.APIConstants.PAGINATION_HEADER_SCHEMA;
import static com.jeanbarcellos.project101.infra.constants.APIConstants.PAGINATION_KEY_CURRENT_PAGE;
import static com.jeanbarcellos.project101.infra.constants.APIConstants.PAGINATION_KEY_PAGES;
import static com.jeanbarcellos.project101.infra.constants.APIConstants.PAGINATION_KEY_PER_PAGE;
import static com.jeanbarcellos.project101.infra.constants.APIConstants.PAGINATION_KEY_TOTAL;
import static com.jeanbarcellos.project101.infra.constants.APIConstants.PARAM_PAGE;
import static com.jeanbarcellos.project101.infra.constants.APIConstants.PARAM_PAGE_DEFAULT;
import static com.jeanbarcellos.project101.infra.constants.APIConstants.PARAM_PAGE_SIZE;
import static com.jeanbarcellos.project101.infra.constants.APIConstants.PARAM_PAGE_SIZE_DEFAULT;
import static com.jeanbarcellos.project101.infra.constants.APIConstants.PARAM_SORT;
import static com.jeanbarcellos.project101.infra.constants.APIConstants.PARAM_SORT_CREATED_DESC;
import static com.jeanbarcellos.project101.infra.constants.APIConstants.STATUS_200_DESCRIPTION;
import static com.jeanbarcellos.project101.infra.constants.APIConstants.STATUS_201_DESCRIPTION;
import static com.jeanbarcellos.project101.infra.constants.APIConstants.STATUS_400_DESCRIPTION;
import static com.jeanbarcellos.project101.infra.constants.APIConstants.STATUS_401_DESCRIPTION;
import static com.jeanbarcellos.project101.infra.constants.APIConstants.STATUS_403_DESCRIPTION;
import static com.jeanbarcellos.project101.infra.constants.APIConstants.STATUS_404_DESCRIPTION;
import static com.jeanbarcellos.project101.infra.constants.APIConstants.STATUS_500_DESCRIPTION;

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

import com.jeanbarcellos.core.PageSortRequest;
import com.jeanbarcellos.core.dto.ErrorResponse;
import com.jeanbarcellos.core.dto.SuccessResponse;
import com.jeanbarcellos.core.web.ControllerBase;
import com.jeanbarcellos.project101.application.dtos.UserFullResponse;
import com.jeanbarcellos.project101.application.dtos.UserInsertRequest;
import com.jeanbarcellos.project101.application.dtos.UserResponse;
import com.jeanbarcellos.project101.application.dtos.UserUpdateRequest;
import com.jeanbarcellos.project101.application.services.UserService;
import com.jeanbarcellos.project101.infra.constants.APIConstants;

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
@RequestMapping("/users")
@PreAuthorize(HAS_ROLE_ROOT)
@Tag(name = "Usuários", description = "Manutenção de usuários")
@SecurityRequirement(name = APIConstants.BEARER_KEY)
public class UserController extends ControllerBase {

	@Autowired
	private UserService userService;

	@GetMapping
	@Operation(summary = "Listar usuários", description = "Lista todos os usuários")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = STATUS_200_DESCRIPTION, headers = {
					@Header(name = PAGINATION_KEY_CURRENT_PAGE, description = PAGINATION_DESCRIPTION_CURRENT_PAGE, schema = @Schema(type = PAGINATION_HEADER_SCHEMA)),
					@Header(name = PAGINATION_KEY_PER_PAGE, description = PAGINATION_DESCRIPTION_PER_PAGE, schema = @Schema(type = PAGINATION_HEADER_SCHEMA)),
					@Header(name = PAGINATION_KEY_PAGES, description = PAGINATION_DESCRIPTION_PAGES, schema = @Schema(type = PAGINATION_HEADER_SCHEMA)),
					@Header(name = PAGINATION_KEY_TOTAL, description = PAGINATION_DESCRIPTION_TOTAL, schema = @Schema(type = PAGINATION_HEADER_SCHEMA))
			}, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = UserResponse.class)))),
			@ApiResponse(responseCode = "400", description = STATUS_400_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "403", description = STATUS_403_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "404", description = STATUS_404_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = STATUS_500_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class)))
	})
	public ResponseEntity<List<UserResponse>> getAll(
			@RequestParam(value = PARAM_PAGE, defaultValue = PARAM_PAGE_DEFAULT, required = false) Integer page,
			@RequestParam(value = PARAM_PAGE_SIZE, defaultValue = PARAM_PAGE_SIZE_DEFAULT, required = false) Integer size,
			@RequestParam(value = PARAM_SORT, defaultValue = PARAM_SORT_CREATED_DESC, required = false) String sort) {
		return this.paginatedResponse(this.userService.getAll(PageSortRequest.of(page, size, sort)));
	}

	@GetMapping(PATH_SHOW)
	@Operation(summary = "Exibir usuário", description = "Exibe detalhes um usuário a partir de um ID informado")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = STATUS_200_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = UserFullResponse.class))),
			@ApiResponse(responseCode = "401", description = STATUS_401_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "403", description = STATUS_403_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "404", description = STATUS_404_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = STATUS_500_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class)))
	})
	public ResponseEntity<UserFullResponse> getById(@PathVariable UUID id) {
		return ResponseEntity.ok(this.userService.getById(id));
	}

	@PostMapping("")
	@Operation(summary = "Incluir usuário", description = "Inclui um novo usuário")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = STATUS_201_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = UserFullResponse.class))),
			@ApiResponse(responseCode = "400", description = STATUS_400_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "401", description = STATUS_401_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "403", description = STATUS_403_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = STATUS_500_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class)))
	})
	public ResponseEntity<UserFullResponse> insert(@RequestBody UserInsertRequest request) {
		var response = this.userService.insert(request);

		return ResponseEntity.created(this.createUriLocation(PATH_SHOW, response.getId())).body(response);
	}

	@PutMapping(PATH_SHOW)
	@Operation(summary = "Alterar usuário", description = "Altera um novo usuário existente")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = STATUS_200_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = UserFullResponse.class))),
			@ApiResponse(responseCode = "400", description = STATUS_400_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "401", description = STATUS_401_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "403", description = STATUS_403_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "404", description = STATUS_404_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = STATUS_500_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class)))
	})
	public ResponseEntity<UserFullResponse> update(@PathVariable UUID id, @RequestBody UserUpdateRequest request) {
		return ResponseEntity.ok(this.userService.update(request.setId(id)));
	}

	@PutMapping(PATH_SHOW + "/activate")
	@Operation(summary = "Ativar usuário", description = "Ativa um usuário atualmente inativado")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = STATUS_200_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = SuccessResponse.class))),
			@ApiResponse(responseCode = "400", description = STATUS_400_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "401", description = STATUS_401_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "403", description = STATUS_403_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "404", description = STATUS_404_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = STATUS_500_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class)))
	})
	public ResponseEntity<SuccessResponse> activate(@PathVariable UUID id) {
		return ResponseEntity.ok(this.userService.activate(id));
	}

	@PutMapping(PATH_SHOW + "/inactivate")
	@Operation(summary = "Inativar usuário", description = "Inativa um usuário atualmente ativado")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = STATUS_200_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = SuccessResponse.class))),
			@ApiResponse(responseCode = "400", description = STATUS_400_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "401", description = STATUS_401_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "403", description = STATUS_403_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "404", description = STATUS_404_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = STATUS_500_DESCRIPTION, content = @Content(mediaType = MEDIA_TYPE_APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class)))
	})
	public ResponseEntity<SuccessResponse> inactivate(@PathVariable UUID id) {
		return ResponseEntity.ok(this.userService.inactivate(id));
	}

}