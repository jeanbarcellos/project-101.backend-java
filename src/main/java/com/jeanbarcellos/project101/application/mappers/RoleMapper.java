package com.jeanbarcellos.project101.application.mappers;

import org.springframework.stereotype.Component;

import com.jeanbarcellos.project101.application.dtos.RoleResponse;
import com.jeanbarcellos.project101.application.dtos.RoleRequest;
import com.jeanbarcellos.project101.application.dtos.RoleSimpleResponse;
import com.jeanbarcellos.project101.domain.entities.Role;

@Component
public class RoleMapper {

    public Role toRole(RoleRequest request) {
        return new Role(request.getName(), request.getDescription());
    }

    public Role copyProperties(Role role, RoleRequest request) {
        return role.setName(request.getName())
                .setDescription(request.getDescription());
    }

    public RoleResponse toResponse(Role role) {
        return RoleResponse.of(role);
    }

    public RoleSimpleResponse toCollectionResponse(Role role) {
        return RoleSimpleResponse.of(role);
    }

}
