package com.jeanbarcellos.demo.domain.repositories;

import java.util.Set;
import java.util.UUID;

import com.jeanbarcellos.demo.domain.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {

    User findByEmail(String email);

    Set<User> findByIdIn(Set<UUID> ids);
}
