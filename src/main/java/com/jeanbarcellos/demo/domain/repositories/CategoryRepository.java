package com.jeanbarcellos.demo.domain.repositories;

import java.util.UUID;

import com.jeanbarcellos.demo.domain.entities.Category;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

}
