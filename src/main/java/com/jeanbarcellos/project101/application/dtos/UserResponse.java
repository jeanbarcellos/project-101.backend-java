package com.jeanbarcellos.project101.application.dtos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.jeanbarcellos.core.util.CollectionUtils;
import com.jeanbarcellos.project101.domain.entities.User;
import com.jeanbarcellos.project101.domain.enums.UserStatus;

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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder.Default
    private List<RoleSimpleResponse> roles = new ArrayList<>();

    public static UserResponse of(User user) {
        return UserResponse
                .builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .status(user.getStatus())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .roles(CollectionUtils.mapToList(user.getRoles(), RoleSimpleResponse::of))
                .build();
    }

    public static List<UserResponse> of(List<User> users) {
        return CollectionUtils.mapToList(users, UserResponse::of);
    }
}
