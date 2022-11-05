package com.jeanbarcellos.demo.application.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.jeanbarcellos.core.dto.SuccessResponse;
import com.jeanbarcellos.core.exception.NotFoundException;
import com.jeanbarcellos.core.exception.ValidationException;
import com.jeanbarcellos.demo.application.dtos.UserFullResponse;
import com.jeanbarcellos.demo.application.dtos.UserRequest;
import com.jeanbarcellos.demo.application.dtos.UserResponse;
import com.jeanbarcellos.demo.application.dtos.UserUpdateRequest;
import com.jeanbarcellos.demo.application.mappers.UserMapper;
import com.jeanbarcellos.demo.domain.entities.User;
import com.jeanbarcellos.demo.domain.repositories.RoleRepository;
import com.jeanbarcellos.demo.domain.repositories.UserRepository;

@Service
public class UserService {

    private static final String MSG_ERROR_USER_NOT_INFORMED = "O ID do usuário deve ser informado.";
    private static final String MSG_ERROR_USER_NOT_FOUND = "Não há usário para o ID informado.";
    private static final String MSG_USER_ACTIVATED_SUCCESSFULLY = "Usuário ativado com sucesso.";
    private static final String MSG_USER_INACTIVATED_SUCCESSFULLY = "Usuário desativado com sucesso.";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public List<UserResponse> getAll() {
        List<User> list = this.userRepository.findAll();

        return UserResponse.of(list);
    }

    public UserFullResponse getById(UUID id) {
        User user = this.findByIdOrThrow(id);

        return UserFullResponse.of(user);
    }

    public UserFullResponse insert(UserRequest request) {
        User user = UserMapper.toUser(request, ids -> this.roleRepository.findByIdIn(ids));

        user.setPassword(this.encoderPassword(request.getPassword()));

        user = this.userRepository.save(user);

        return UserFullResponse.of(user);
    }

    public UserFullResponse update(UserUpdateRequest request) {
        this.validateExistsById(request.getId());

        User user = this.findByIdOrThrow(request.getId());

        UserMapper.copyProperties(user, request, ids -> this.roleRepository.findByIdIn(ids));

        this.userRepository.save(user);

        return UserFullResponse.of(user);
    }

    public SuccessResponse activate(UUID id) {
        User user = this.findByIdOrThrow(id);

        user.activate();

        this.userRepository.save(user);

        return SuccessResponse.create(MSG_USER_ACTIVATED_SUCCESSFULLY);
    }

    public SuccessResponse inactivate(UUID id) {
        User user = this.findByIdOrThrow(id);

        user.inactivate();

        this.userRepository.save(user);

        return SuccessResponse.create(MSG_USER_INACTIVATED_SUCCESSFULLY);
    }

    private String encoderPassword(String password) {
        return this.passwordEncoder.encode(password);
    }

    private User findByIdOrThrow(UUID id) {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(MSG_ERROR_USER_NOT_FOUND));
    }

    private void validateExistsById(UUID id) {
        if (ObjectUtils.isEmpty(id)) {
            throw new ValidationException(MSG_ERROR_USER_NOT_INFORMED);
        }

        if (!this.userRepository.existsById(id)) {
            throw new NotFoundException(MSG_ERROR_USER_NOT_FOUND);
        }
    }
}
