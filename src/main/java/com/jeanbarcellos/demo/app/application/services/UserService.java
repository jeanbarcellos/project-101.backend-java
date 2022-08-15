package com.jeanbarcellos.demo.app.application.services;

import static org.springframework.util.ObjectUtils.isEmpty;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jeanbarcellos.demo.app.application.dtos.UserRequest;
import com.jeanbarcellos.demo.app.application.dtos.UserResponse;
import com.jeanbarcellos.demo.app.application.mappers.UserMapper;
import com.jeanbarcellos.demo.app.domain.entities.Role;
import com.jeanbarcellos.demo.app.domain.entities.User;
import com.jeanbarcellos.demo.app.domain.repositories.RoleRepository;
import com.jeanbarcellos.demo.app.domain.repositories.UserRepository;
import com.jeanbarcellos.demo.core.dtos.SuccessResponse;
import com.jeanbarcellos.demo.core.exceptions.NotFoundException;
import com.jeanbarcellos.demo.core.exceptions.ValidationException;

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
        List<User> list = userRepository.findAll();

        return UserResponse.from(list);
    }

    public UserResponse getById(UUID id) {
        User user = this.getUser(id);

        return UserMapper.toResponse(user);
    }

    public UserResponse insert(UserRequest request) {
        List<Role> roles = roleRepository.findByIdIn(request.getRoles());

        User user = UserMapper.toUser(request, roles);

        user.setPassword(this.encoderPassword(request.getPassword()));

        user = userRepository.save(user);

        return UserMapper.toResponse(user);
    }

    public UserResponse update(UUID id, UserRequest request) {
        this.validateExistsById(id);

        List<Role> roles = roleRepository.findByIdIn(request.getRoles());

        User user = UserMapper.toUser(id, request, roles);

        user.setPassword(this.encoderPassword(request.getPassword()));

        user = userRepository.save(user);

        return UserMapper.toResponse(user);
    }

    public SuccessResponse activate(UUID id) {
        User user = this.getUser(id);
        user.activate();

        userRepository.save(user);

        return SuccessResponse.create(MSG_USER_ACTIVATED_SUCCESSFULLY);
    }

    public SuccessResponse inactivate(UUID id) {
        User user = this.getUser(id);
        user.inactivate();

        userRepository.save(user);

        return SuccessResponse.create(MSG_USER_INACTIVATED_SUCCESSFULLY);
    }

    private User getUser(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(MSG_ERROR_USER_NOT_FOUND));
    }

    private String encoderPassword(String password) {
        return this.passwordEncoder.encode(password);
    }

    private void validateExistsById(UUID id) {
        if (isEmpty(id)) {
            throw new ValidationException(MSG_ERROR_USER_NOT_INFORMED);
        }

        if (!userRepository.existsById(id)) {
            throw new NotFoundException(MSG_ERROR_USER_NOT_FOUND);
        }
    }
}
