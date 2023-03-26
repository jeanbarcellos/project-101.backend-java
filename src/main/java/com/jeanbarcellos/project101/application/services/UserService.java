package com.jeanbarcellos.project101.application.services;

import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.jeanbarcellos.core.dto.SuccessResponse;
import com.jeanbarcellos.core.exception.NotFoundException;
import com.jeanbarcellos.core.exception.ValidationException;
import com.jeanbarcellos.project101.application.dtos.UserResponse;
import com.jeanbarcellos.project101.application.dtos.UserRequest;
import com.jeanbarcellos.project101.application.dtos.UserSimpleResponse;
import com.jeanbarcellos.project101.application.dtos.UserUpdateRequest;
import com.jeanbarcellos.project101.application.mappers.UserMapper;
import com.jeanbarcellos.project101.domain.entities.User;
import com.jeanbarcellos.project101.domain.repositories.RoleRepository;
import com.jeanbarcellos.project101.domain.repositories.UserRepository;

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
    private UserMapper userMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        this.userMapper.setProviderFindRoleByNameIn(this.roleRepository::findByNameIn);
    }

    public List<UserSimpleResponse> getAll() {
        List<User> list = this.userRepository.findAll();

        return UserSimpleResponse.of(list);
    }

    public UserResponse getById(UUID id) {
        User user = this.findByIdOrThrow(id);

        return UserResponse.of(user);
    }

    public UserResponse insert(UserRequest request) {
        User user = this.userMapper.toUser(request);

        user.setPassword(this.encoderPassword(request.getPassword()));

        user = this.userRepository.save(user);

        return UserResponse.of(user);
    }

    public UserResponse update(UserUpdateRequest request) {
        this.validateExistsById(request.getId());

        User user = this.findByIdOrThrow(request.getId());

        this.userMapper.copyProperties(user, request);

        this.userRepository.save(user);

        return UserResponse.of(user);
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
