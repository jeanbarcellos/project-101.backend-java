package com.jeanbarcellos.demo.application.dtos;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.jeanbarcellos.demo.domain.entities.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleCollectionResponse {

    private UUID id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static RoleCollectionResponse from(Role role) {
        return RoleCollectionResponse
                .builder()
                .id(role.getId())
                .name(role.getName())
                .description(role.getDescription())
                .createdAt(role.getCreatedAt())
                .updatedAt(role.getUpdatedAt())
                .build();
    }

    public static List<RoleCollectionResponse> from(List<Role> categories) {
        return categories.stream().map(RoleCollectionResponse::from).collect(Collectors.toList());
    }
}
