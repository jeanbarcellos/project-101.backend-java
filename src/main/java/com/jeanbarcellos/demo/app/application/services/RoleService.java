package com.jeanbarcellos.demo.app.application.services;

import static org.springframework.util.ObjectUtils.isEmpty;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.jeanbarcellos.demo.app.application.dtos.RoleCollectionResponse;
import com.jeanbarcellos.demo.app.application.dtos.RoleRequest;
import com.jeanbarcellos.demo.app.application.dtos.RoleResponse;
import com.jeanbarcellos.demo.app.application.mappers.RoleMapper;
import com.jeanbarcellos.demo.app.domain.entities.Role;
import com.jeanbarcellos.demo.app.domain.repositories.RoleRepository;
import com.jeanbarcellos.demo.core.dtos.SuccessResponse;
import com.jeanbarcellos.demo.core.exceptions.NotFoundException;
import com.jeanbarcellos.demo.core.exceptions.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private static final String MSG_ERROR_ROLE_NOT_INFORMED = "O ID do perfil deve ser informado.";
    private static final String MSG_ERROR_ROLE_NOT_FOUND = "Não há perfil para o ID informado.";
    private static final String MSG_ERROR_ROLE_INHERIT_NOT_FOUND = "Perfil para herdar não encontrada.";
    private static final String MSG_ROLE_DELETED_SUCCESSFULLY = "O perfil excluído com sucesso.";

    @Autowired
    private RoleRepository roleRepository;

    public List<RoleCollectionResponse> getAll() {
        List<Role> list = roleRepository.findAll();

        return list.stream().map(RoleCollectionResponse::from).collect(Collectors.toList());
    }

    public RoleResponse getById(UUID id) {
        Role result = this.getRole(id);

        return RoleMapper.toResponse(result);
    }

    public RoleResponse insert(RoleRequest request) {
        Role role = RoleMapper.toRole(request);

        this.assignChildRoles(role, request.getChildRoles());

        role = roleRepository.save(role);

        return RoleMapper.toResponse(role);
    }

    public RoleResponse update(UUID id, RoleRequest request) {
        this.validateExistsById(id);

        Role role = this.getRole(id);

        RoleMapper.updateFromRequest(role, request);

        this.assignChildRoles(role, request.getChildRoles());

        roleRepository.save(role);

        return RoleMapper.toResponse(role);
    }

    private void assignChildRoles(Role role, List<UUID> rolesIds) {
        role.clearChildRoles();

        for (UUID roleId : rolesIds) {
            var childRole = this.roleRepository.findById(roleId).get();

            if (childRole == null) {
                throw new NotFoundException(MSG_ERROR_ROLE_INHERIT_NOT_FOUND);
            }

            role.addChild(childRole);
        }
    }

    public SuccessResponse delete(UUID id) {
        this.validateExistsById(id);

        roleRepository.deleteById(id);

        return SuccessResponse.create(MSG_ROLE_DELETED_SUCCESSFULLY);
    }

    private Role getRole(UUID id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(MSG_ERROR_ROLE_NOT_FOUND));
    }

    private void validateExistsById(UUID id) {
        if (isEmpty(id)) {
            throw new ValidationException(MSG_ERROR_ROLE_NOT_INFORMED);
        }

        if (!roleRepository.existsById(id)) {
            throw new NotFoundException(MSG_ERROR_ROLE_NOT_FOUND);
        }
    }
}
