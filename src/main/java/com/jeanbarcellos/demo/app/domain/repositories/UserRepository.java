package com.jeanbarcellos.demo.app.domain.repositories;

import java.util.Set;
import java.util.UUID;

import com.jeanbarcellos.demo.app.domain.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {

    User findByEmail(String email);

    Set<User> findByIdIn(Set<UUID> ids);
}
