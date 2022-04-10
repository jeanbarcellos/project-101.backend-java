package com.jeanbarcellos.demo.app.application.dtos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.jeanbarcellos.demo.app.domain.entities.Role;

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
        public LocalDateTime createdAt;
        public LocalDateTime updatedAt;

        @Builder.Default
        private List<UUID> childRoleIds = new ArrayList<UUID>();

        @Builder.Default
        private List<UUID> parentRoleIds = new ArrayList<UUID>();

        public static RoleResponse from(Role role) {
                return RoleResponse
                                .builder()
                                .id(role.getId())
                                .name(role.getName())
                                .description(role.getDescription())
                                .createdAt(role.getCreatedAt())
                                .updatedAt(role.getUpdatedAt())
                                .childRoleIds(
                                                role.getChildRoles().stream().map(childRole -> childRole.getId())
                                                                .collect(Collectors.toList()))
                                .parentRoleIds(role.getParentRoles().stream().map(parentRole -> parentRole.getId())
                                                .collect(Collectors.toList()))
                                .build();
        }
}
