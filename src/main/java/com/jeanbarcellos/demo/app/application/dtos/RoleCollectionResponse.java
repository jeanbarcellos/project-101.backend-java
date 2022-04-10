package com.jeanbarcellos.demo.app.application.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

import com.jeanbarcellos.demo.app.domain.entities.Role;

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
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;

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
}
