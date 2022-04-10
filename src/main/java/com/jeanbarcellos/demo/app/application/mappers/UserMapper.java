package com.jeanbarcellos.demo.app.application.mappers;

import java.util.List;
import java.util.UUID;

import com.jeanbarcellos.demo.app.application.dtos.UserRequest;
import com.jeanbarcellos.demo.app.application.dtos.UserResponse;
import com.jeanbarcellos.demo.app.domain.entities.Role;
import com.jeanbarcellos.demo.app.domain.entities.User;

public class UserMapper {

    public static User toUser(UserRequest request) {
        var user = new User(
                request.getName(),
                request.getEmail(),
                request.getPassword(),
                request.getStatus());

        return user;
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
