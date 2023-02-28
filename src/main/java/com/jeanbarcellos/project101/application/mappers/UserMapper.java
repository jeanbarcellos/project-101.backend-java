package com.jeanbarcellos.project101.application.mappers;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.jeanbarcellos.project101.application.dtos.UserRequest;
import com.jeanbarcellos.project101.application.dtos.UserResponse;
import com.jeanbarcellos.project101.application.dtos.UserUpdateRequest;
import com.jeanbarcellos.project101.domain.entities.Role;
import com.jeanbarcellos.project101.domain.entities.User;

@Component
public class UserMapper {

    private Function<List<UUID>, List<Role>> providerFindByIdIn;

    public UserMapper setProviderFindByIdIn(Function<List<UUID>, List<Role>> provider) {
        this.providerFindByIdIn = provider;
        return this;
    }

    public User toUser(UserRequest request) {
        var user = new User(
                request.getName(),
                request.getEmail(),
                request.getPassword(),
                request.getStatus());

        List<Role> roles = providerFindByIdIn.apply(request.getRoles());

        for (Role role : roles) {
            user.addRole(role);
        }

        return user;
    }

    public User copyProperties(User user, UserUpdateRequest request) {
        user.setName(request.getName())
                .setEmail(request.getEmail())
                .setStatus(request.getStatus());

        for (Role role : providerFindByIdIn.apply(request.getRoles())) {
            user.addRole(role);
        }

        return user;
    }

    public UserResponse toUserResponse(User user) {
        return UserResponse.of(user);
    }

}
