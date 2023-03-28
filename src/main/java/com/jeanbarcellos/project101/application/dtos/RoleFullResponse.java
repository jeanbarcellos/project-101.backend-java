package com.jeanbarcellos.project101.application.dtos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.jeanbarcellos.core.util.CollectionUtils;
import com.jeanbarcellos.project101.domain.entities.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleFullResponse {

    private UUID id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder.Default
    private List<String> childRoles = new ArrayList<>();

    @Builder.Default
    private List<String> parentRoles = new ArrayList<>();

    public static RoleFullResponse of(Role role) {
        return RoleFullResponse
                .builder()
                .id(role.getId())
                .name(role.getName())
                .description(role.getDescription())
                .createdAt(role.getCreatedAt())
                .updatedAt(role.getUpdatedAt())
                .childRoles(role.getChildRolesNames())
                .parentRoles(role.getParentRolesNames())
                .build();
    }

    public static List<RoleFullResponse> of(List<Role> categories) {
        return CollectionUtils.mapToList(categories, RoleFullResponse::of);
    }
}
