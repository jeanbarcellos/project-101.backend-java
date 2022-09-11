package com.jeanbarcellos.demo.application.mappers;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;

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

    public static User toUser(UserRequest request, Function<List<UUID>, List<Role>> findByIdIn) {
        var user = toUser(request);

        List<Role> roles = findByIdIn.apply(request.getRoles());

        for (Role role : roles) {
            user.addRole(role);
        }

        return user;
    }

    public static User toUser(UUID id, UserRequest request, Function<List<UUID>, List<Role>> findByIdIn) {
        var user = new User(
                id,
                request.getName(),
                request.getEmail(),
                request.getPassword(),
                request.getStatus());

        List<Role> roles = findByIdIn.apply(request.getRoles());

        for (Role role : roles) {
            user.addRole(role);
        }
        return user;
    }

    public static UserResponse toUserResponse(User user) {
        return UserResponse.from(user);
    }

}
