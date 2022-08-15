package com.jeanbarcellos.demo.application.mappers;

import java.util.List;
import java.util.UUID;

import com.jeanbarcellos.demo.application.dtos.UserRequest;
import com.jeanbarcellos.demo.application.dtos.UserResponse;
import com.jeanbarcellos.demo.domain.entities.Role;
import com.jeanbarcellos.demo.domain.entities.User;

public class UserMapper {

    private UserMapper() {
    }

    public static User toUser(UserRequest request) {
        return new User(
                request.getName(),
                request.getEmail(),
                request.getPassword(),
                request.getStatus());
    }

    public static User toUser(UserRequest request, List<Role> roles) {
        var user = new User(
                request.getName(),
                request.getEmail(),
                request.getPassword(),
                request.getStatus());

        for (Role role : roles) {
            user.addRole(role);
        }

        return user;
    }

    public static User toUser(UUID id, UserRequest request, List<Role> roles) {
        var user = new User(
                id,
                request.getName(),
                request.getEmail(),
                request.getPassword(),
                request.getStatus());

        for (Role role : roles) {
            user.addRole(role);
        }

        return user;
    }

    public static UserResponse toResponse(User user) {
        return UserResponse.from(user);
    }

}
