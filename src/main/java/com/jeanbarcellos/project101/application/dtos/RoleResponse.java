package com.jeanbarcellos.project101.application.dtos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.jeanbarcellos.core.util.CollectionUtils;
import com.jeanbarcellos.project101.domain.entities.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponse {

    private UUID id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder.Default
    private List<UUID> childRoleIds = new ArrayList<>();

    @Builder.Default
    private List<UUID> parentRoleIds = new ArrayList<>();

    public static RoleResponse of(Role role) {
        return RoleResponse
                .builder()
                .id(role.getId())
                .name(role.getName())
                .description(role.getDescription())
                .createdAt(role.getCreatedAt())
                .updatedAt(role.getUpdatedAt())
                .childRoleIds(role.getChildRolesIds())
                .parentRoleIds(role.getParentRolesIds())
                .build();
    }

    public static List<RoleResponse> of(List<Role> categories) {
        return CollectionUtils.mapToList(categories, RoleResponse::of);
    }
}
