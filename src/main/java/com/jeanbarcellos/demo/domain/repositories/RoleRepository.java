package com.jeanbarcellos.demo.domain.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jeanbarcellos.demo.domain.entities.Role;

public interface RoleRepository extends JpaRepository<Role, UUID> {

    public List<Role> findByIdIn(List<UUID> ids);
}
