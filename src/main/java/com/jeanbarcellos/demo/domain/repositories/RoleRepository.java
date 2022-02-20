package com.jeanbarcellos.demo.domain.repositories;

import java.util.List;
import java.util.UUID;

import com.jeanbarcellos.demo.domain.entities.Role;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, UUID> {

    List<Role> findByIdIn(List<UUID> ids);
}
