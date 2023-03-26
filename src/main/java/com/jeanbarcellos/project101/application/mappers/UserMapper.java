package com.jeanbarcellos.project101.application.mappers;

import java.util.List;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.jeanbarcellos.project101.application.dtos.UserRequest;
import com.jeanbarcellos.project101.application.dtos.UserSimpleResponse;
import com.jeanbarcellos.project101.application.dtos.UserUpdateRequest;
import com.jeanbarcellos.project101.domain.entities.Role;
import com.jeanbarcellos.project101.domain.entities.User;

import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true)
@Component
public class UserMapper {

    private Function<List<String>, List<Role>> providerFindRoleByNameIn;

    public User toUser(UserRequest request) {
        var user = new User(
                request.getName(),
                request.getEmail(),
                request.getPassword(),
                request.getStatus());

        List<Role> roles = providerFindRoleByNameIn.apply(request.getRoles());

        for (Role role : roles) {
            user.addRole(role);
        }

        return user;
    }

    public User copyProperties(User user, UserUpdateRequest request) {
        user.setName(request.getName())
                .setEmail(request.getEmail())
                .setStatus(request.getStatus());

        for (Role role : providerFindRoleByNameIn.apply(request.getRoles())) {
            user.addRole(role);
        }

        return user;
    }

    public UserSimpleResponse toUserResponse(User user) {
        return UserSimpleResponse.of(user);
    }

}
