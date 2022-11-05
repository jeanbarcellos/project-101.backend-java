package com.jeanbarcellos.demo.application.dtos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.jeanbarcellos.core.domain.EntityBase;
import com.jeanbarcellos.core.util.CollectionUtils;
import com.jeanbarcellos.demo.domain.entities.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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
    private List<UUID> childRoleIds = new ArrayList<>();

    @Builder.Default
    private List<UUID> parentRoleIds = new ArrayList<>();

    public static RoleFullResponse of(Role role) {
        return RoleFullResponse
                .builder()
                .id(role.getId())
                .name(role.getName())
                .description(role.getDescription())
                .createdAt(role.getCreatedAt())
                .updatedAt(role.getUpdatedAt())
                .childRoleIds(CollectionUtils.mapToList(role.getChildRoles(), EntityBase::getId))
                .parentRoleIds(CollectionUtils.mapToList(role.getParentRoles(), EntityBase::getId))
                .build();
    }

    public static List<RoleFullResponse> of(List<Role> categories) {
        return CollectionUtils.mapToList(categories, RoleFullResponse::of);
    }
}
