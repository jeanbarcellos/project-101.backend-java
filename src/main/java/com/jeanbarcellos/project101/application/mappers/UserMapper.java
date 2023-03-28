package com.jeanbarcellos.project101.application.mappers;

import java.util.List;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.jeanbarcellos.project101.application.dtos.UserInsertRequest;
import com.jeanbarcellos.project101.application.dtos.UserResponse;
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

    public User toUser(UserInsertRequest request) {
        var user = new User(
                request.getName(),
                request.getEmail(),
                request.getPassword(),
                request.getStatus());

        user.addRoles(this.providerFindRoleByNameIn.apply(request.getRoles()));

        return user;
    }

    public User copyProperties(User user, UserUpdateRequest request) {
        user.setName(request.getName())
                .setEmail(request.getEmail())
                .setStatus(request.getStatus());

        user.addRoles(this.providerFindRoleByNameIn.apply(request.getRoles()));

        return user;
    }

    public UserResponse toUserResponse(User user) {
        return UserResponse.of(user);
    }

}
