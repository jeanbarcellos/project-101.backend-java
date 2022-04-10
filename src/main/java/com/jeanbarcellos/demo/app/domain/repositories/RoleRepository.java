package com.jeanbarcellos.demo.app.domain.repositories;

import java.util.List;
import java.util.UUID;

import com.jeanbarcellos.demo.app.domain.entities.Role;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, UUID> {

    List<Role> findByIdIn(List<UUID> ids);
}
