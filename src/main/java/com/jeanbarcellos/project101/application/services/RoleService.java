package com.jeanbarcellos.project101.application.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.jeanbarcellos.core.dto.SuccessResponse;
import com.jeanbarcellos.core.exception.NotFoundException;
import com.jeanbarcellos.core.exception.ValidationException;
import com.jeanbarcellos.project101.application.dtos.RoleRequest;
import com.jeanbarcellos.project101.application.dtos.RoleResponse;
import com.jeanbarcellos.project101.application.dtos.RoleSimpleResponse;
import com.jeanbarcellos.project101.application.mappers.RoleMapper;
import com.jeanbarcellos.project101.domain.entities.Role;
import com.jeanbarcellos.project101.domain.repositories.RoleRepository;

@Service
public class RoleService {

    private static final String MSG_ERROR_ROLE_NOT_INFORMED = "O ID do perfil deve ser informado.";
    private static final String MSG_ERROR_ROLE_NOT_FOUND = "Não há perfil para o ID informado.";
    private static final String MSG_ERROR_ROLE_INHERIT_NOT_FOUND = "Perfil para herdar não encontrado.";
    private static final String MSG_ROLE_DELETED_SUCCESSFULLY = "O perfil excluído com sucesso.";

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleMapper roleMapper;

    public List<RoleSimpleResponse> getAll() {
        List<Role> list = this.roleRepository.findAll();

        return RoleSimpleResponse.of(list);
    }

    public RoleResponse getById(UUID id) {
        Role result = this.findByIdOrThrow(id);

        return RoleResponse.of(result);
    }

    public RoleResponse insert(RoleRequest request) {
        Role role = this.roleMapper.toRole(request);

        this.assignChildRoles(role, request.getChildRoles());

        role = this.roleRepository.save(role);

        return RoleResponse.of(role);
    }

    public RoleResponse update(UUID id, RoleRequest request) {
        this.validateExistsById(id);

        Role role = this.findByIdOrThrow(id);

        this.roleMapper.copyProperties(role, request);

        this.assignChildRoles(role, request.getChildRoles());

        this.roleRepository.save(role);

        return RoleResponse.of(role);
    }

    private void assignChildRoles(Role role, List<String> rolesNames) {
        role.clearChildRoles();

        for (String roleName : rolesNames) {
            var childRole = this.roleRepository.findByName(roleName)
                    .orElseThrow(() -> new NotFoundException(MSG_ERROR_ROLE_INHERIT_NOT_FOUND));

            role.addChild(childRole);
        }
    }

    public SuccessResponse delete(UUID id) {
        this.validateExistsById(id);

        this.roleRepository.deleteById(id);

        return SuccessResponse.create(MSG_ROLE_DELETED_SUCCESSFULLY);
    }

    private Role findByIdOrThrow(UUID id) {
        return this.roleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(MSG_ERROR_ROLE_NOT_FOUND));
    }

    private void validateExistsById(UUID id) {
        if (ObjectUtils.isEmpty(id)) {
            throw new ValidationException(MSG_ERROR_ROLE_NOT_INFORMED);
        }

        if (!this.roleRepository.existsById(id)) {
            throw new NotFoundException(MSG_ERROR_ROLE_NOT_FOUND);
        }
    }
}
