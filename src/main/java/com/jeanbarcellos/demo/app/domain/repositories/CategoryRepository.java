package com.jeanbarcellos.demo.app.domain.repositories;

import java.util.UUID;

import com.jeanbarcellos.demo.app.domain.entities.Category;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

}
