package com.jeanbarcellos.demo.app.application.mappers;

import com.jeanbarcellos.demo.app.application.dtos.RoleCollectionResponse;
import com.jeanbarcellos.demo.app.application.dtos.RoleRequest;
import com.jeanbarcellos.demo.app.application.dtos.RoleResponse;
import com.jeanbarcellos.demo.app.domain.entities.Role;

public class RoleMapper {

    public static Role toRole(RoleRequest request) {
        return new Role(request.getName(), request.getDescription());
    }

    public static Role updateFromRequest(Role role, RoleRequest request) {
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
