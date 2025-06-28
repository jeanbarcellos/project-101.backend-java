package com.jeanbarcellos.core.web;

import java.net.URI;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jeanbarcellos.core.constants.ApiConstants;

public abstract class ControllerBase {

    protected static final String PATH_ROOT = "/";
    protected static final String PATH_SHOW = PATH_ROOT + "{id}";

    protected URI createUriLocation(String path, Object... uriVariableValues) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path(path)
                .buildAndExpand(uriVariableValues)
                .toUri();
    }

    protected <T extends Object> ResponseEntity<List<T>> paginatedResponse(Page<T> response) {
        return ResponseEntity.ok()
                .headers(this.paginationHeaders(response))
                .body(response.getContent());
    }

    protected HttpHeaders paginationHeaders(Page<?> response) {
        var headers = new HttpHeaders();
        headers.set(ApiConstants.PAGINATION_CURRENT_PAGE_KEY, String.valueOf(response.getNumber() + 1));
        headers.set(ApiConstants.PAGINATION_PAGE_SIZE_KEY, String.valueOf(response.getSize()));
        headers.set(ApiConstants.PAGINATION_TOTAL_PAGES_KEY, String.valueOf(response.getTotalPages()));
        headers.set(ApiConstants.PAGINATION_TOTAL_COUNT_KEY, String.valueOf(response.getTotalElements()));
        return headers;
    }

}
