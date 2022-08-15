package com.jeanbarcellos.core.web;

import java.net.URI;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public abstract class ControllerBase {

    protected URI createUriLocation(String path, Object... uriVariableValues) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path(path)
                .buildAndExpand(uriVariableValues)
                .toUri();
    }

}
