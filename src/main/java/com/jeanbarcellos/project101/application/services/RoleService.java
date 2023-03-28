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
import com.jeanbarcellos.project101.application.dtos.RoleFullResponse;
import com.jeanbarcellos.project101.application.dtos.RoleResponse;
import com.jeanbarcellos.project101.application.mappers.RoleMapper;
import com.jeanbarcellos.project101.domain.entities.Role;
import com.jeanbarcellos.project101.domain.repositories.RoleRepository;

@Service
public class RoleService {

    private static final String MSG_ERROR_ROLE_NOT_INFORMED = "O ID do perfil deve ser informado.";
    private static final String MSG_ERROR_ROLE_NOT_FOUND = "Não há perfil para o ID informado. -> %s";
    private static final String MSG_ERROR_ROLE_INHERIT_NOT_FOUND = "Perfil para herdar não encontrado.";
    private static final String MSG_ROLE_DELETED_SUCCESSFULLY = "O perfil '%s' excluído com sucesso.";

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleMapper roleMapper;

    public List<RoleResponse> getAll() {
        return RoleResponse.of(this.roleRepository.findAll());
    }

    public RoleFullResponse getById(UUID id) {
        return RoleFullResponse.of(this.findByIdOrThrow(id));
    }

    public RoleFullResponse insert(RoleRequest request) {
        var role = this.roleMapper.toRole(request);

        this.assignChildRoles(role, request.getChildRoles());

        role = this.roleRepository.save(role);

        return RoleFullResponse.of(role);
    }

    public RoleFullResponse update(RoleRequest request) {
        this.validateExistsById(request.getId());

        var role = this.findByIdOrThrow(request.getId());

        this.roleMapper.copyProperties(role, request);

        this.assignChildRoles(role, request.getChildRoles());

        role = this.roleRepository.save(role);

        return RoleFullResponse.of(role);
    }

    public SuccessResponse delete(UUID id) {
        this.validateExistsById(id);

        this.roleRepository.deleteById(id);

        return SuccessResponse.of(String.format(MSG_ROLE_DELETED_SUCCESSFULLY, id));
    }

    private void assignChildRoles(Role role, List<String> rolesNames) {
        role.clearChildRoles();

        for (String roleName : rolesNames) {
            var childRole = this.roleRepository.findByName(roleName)
                    .orElseThrow(() -> new NotFoundException(MSG_ERROR_ROLE_INHERIT_NOT_FOUND));

            role.addChild(childRole);
        }
    }

    private Role findByIdOrThrow(UUID id) {
        return this.roleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(MSG_ERROR_ROLE_NOT_FOUND, id)));
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
