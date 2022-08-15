package com.jeanbarcellos.demo.application.dtos;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.jeanbarcellos.demo.domain.entities.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationLoginResponse {

    private UUID id;

    private String name;

    private String email;

    @Builder.Default
    private List<String> roles = new ArrayList<>();

    private String token;

    public static AuthenticationLoginResponse from(User user, String token) {
        return AuthenticationLoginResponse
                .builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .token(token)
                .roles(user.getReachableRoleNames())
                .build();
    }

}
