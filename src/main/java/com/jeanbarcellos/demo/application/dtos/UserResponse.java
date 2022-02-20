package com.jeanbarcellos.demo.application.dtos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.jeanbarcellos.demo.domain.entities.User;
import com.jeanbarcellos.demo.domain.enums.UserStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private UUID id;
    private String name;
    private String email;
    private UserStatus status;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;

    @Builder.Default
    private List<RoleCollectionResponse> roles = new ArrayList<RoleCollectionResponse>();

    public static UserResponse from(User user) {
        return UserResponse
                .builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .status(user.getStatus())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .roles(user.getRoles().stream().map(RoleCollectionResponse::from).collect(Collectors.toList()))
                .build();
    }
}
