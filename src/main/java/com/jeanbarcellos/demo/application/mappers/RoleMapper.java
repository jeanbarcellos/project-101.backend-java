package com.jeanbarcellos.demo.application.mappers;

import org.springframework.stereotype.Component;

import com.jeanbarcellos.demo.application.dtos.RoleFullResponse;
import com.jeanbarcellos.demo.application.dtos.RoleRequest;
import com.jeanbarcellos.demo.application.dtos.RoleResponse;
import com.jeanbarcellos.demo.domain.entities.Role;

@Component
public class RoleMapper {

    public Role toRole(RoleRequest request) {
        return new Role(request.getName(), request.getDescription());
    }

    public Role copyProperties(Role role, RoleRequest request) {
        return role.setName(request.getName())
                .setDescription(request.getDescription());
    }

    public RoleFullResponse toResponse(Role role) {
        return RoleFullResponse.of(role);
    }

    public RoleResponse toCollectionResponse(Role role) {
        return RoleResponse.of(role);
    }

}
