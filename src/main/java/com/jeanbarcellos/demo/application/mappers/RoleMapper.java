package com.jeanbarcellos.demo.application.mappers;

import com.jeanbarcellos.demo.application.dtos.RoleCollectionResponse;
import com.jeanbarcellos.demo.application.dtos.RoleRequest;
import com.jeanbarcellos.demo.application.dtos.RoleResponse;
import com.jeanbarcellos.demo.domain.entities.Role;

public class RoleMapper {

    private RoleMapper() {
    }

    public static Role toRole(RoleRequest request) {
        return new Role(request.getName(), request.getDescription());
    }

    public static Role copyProperties(Role role, RoleRequest request) {
        return role.setName(request.getName())
                .setDescription(request.getDescription());
    }

    public static RoleResponse toResponse(Role role) {
        return RoleResponse.from(role);
    }

    public static RoleCollectionResponse toCollectionResponse(Role role) {
        return RoleCollectionResponse.from(role);
    }

}
