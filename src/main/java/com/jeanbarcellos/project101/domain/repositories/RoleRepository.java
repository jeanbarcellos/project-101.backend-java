package com.jeanbarcellos.project101.domain.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jeanbarcellos.project101.domain.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    public List<Role> findByIdIn(List<UUID> ids);

    public List<Role> findByNameIn(List<String> names);

    public Optional<Role> findByName(String name);
}
