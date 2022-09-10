package com.jeanbarcellos.demo.application.services;

import static org.springframework.util.ObjectUtils.isEmpty;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jeanbarcellos.core.dtos.SuccessResponse;
import com.jeanbarcellos.core.exceptions.NotFoundException;
import com.jeanbarcellos.core.exceptions.ValidationException;
import com.jeanbarcellos.demo.application.dtos.UserRequest;
import com.jeanbarcellos.demo.application.dtos.UserResponse;
import com.jeanbarcellos.demo.application.mappers.UserMapper;
import com.jeanbarcellos.demo.domain.entities.Role;
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

        return UserResponse.from(list);
    }

    public UserResponse getById(UUID id) {
        User user = this.findByIdOrThrow(id);

        return UserResponse.from(user);
    }

    public UserResponse insert(UserRequest request) {
        List<Role> roles = this.roleRepository.findByIdIn(request.getRoles());

        User user = UserMapper.toUser(request, roles);

        user.setPassword(this.encoderPassword(request.getPassword()));

        user = this.userRepository.save(user);

        return UserResponse.from(user);
    }

    public UserResponse update(UUID id, UserRequest request) {
        this.validateExistsById(id);

        List<Role> roles = this.roleRepository.findByIdIn(request.getRoles());

        User user = UserMapper.toUser(id, request, roles);

        user.setPassword(this.encoderPassword(request.getPassword()));

        user = this.userRepository.save(user);

        return UserResponse.from(user);
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
        if (isEmpty(id)) {
            throw new ValidationException(MSG_ERROR_USER_NOT_INFORMED);
        }

        if (!this.userRepository.existsById(id)) {
            throw new NotFoundException(MSG_ERROR_USER_NOT_FOUND);
        }
    }
}
