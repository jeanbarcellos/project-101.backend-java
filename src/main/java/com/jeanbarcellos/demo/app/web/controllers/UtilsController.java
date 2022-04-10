package com.jeanbarcellos.demo.app.web.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.jeanbarcellos.demo.config.Roles;
import com.jeanbarcellos.demo.core.web.ControllerBase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/utils")
@PreAuthorize("hasRole('" + Roles.ROOT + "')")
public class UtilsController extends ControllerBase {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/guid-generate")
    public ResponseEntity<?> generateGuid() {
        String guid = UUID.randomUUID().toString();

        Map<String, Object> response = new HashMap<>();
        response.put("guid", guid);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/password-encode")
    public ResponseEntity<?> passwordEncode(@RequestBody HashMap<String, String> request) {
        var passwordHash = passwordEncoder.encode(request.get("password"));

        Map<String, Object> response = new HashMap<>();
        response.put("hash", passwordHash);

        return ResponseEntity.ok(response);
    }

}
