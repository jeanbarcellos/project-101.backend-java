package com.jeanbarcellos.project101.presentation.web.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jeanbarcellos.core.web.ControllerBase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/status")
@Tag(name = "Status", description = "Status da API")
public class StatusController extends ControllerBase {

    @Value("${spring.application.name}")
    private String appName;

    @GetMapping("")
    @Operation(summary = "Consultar Status", description = "Retorna o status da API")
    public ResponseEntity<Map<String, Object>> showStatus() {

        var response = new HashMap<String, Object>();
        response.put("service", this.appName);
        response.put("status", "up");
        response.put("httpStatus", HttpStatus.OK.value());

        return ResponseEntity.ok(response);
    }

}
