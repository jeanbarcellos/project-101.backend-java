package com.jeanbarcellos.core.web;

import java.net.URI;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public abstract class ControllerBase {

    protected static final String PATH_ROOT = "/";
    protected static final String PATH_SHOW = PATH_ROOT + "{id}";

    protected URI createUriLocation(String path, Object... uriVariableValues) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path(path)
                .buildAndExpand(uriVariableValues)
                .toUri();
    }

}
