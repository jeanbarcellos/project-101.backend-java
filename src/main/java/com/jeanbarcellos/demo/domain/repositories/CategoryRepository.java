package com.jeanbarcellos.demo.domain.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jeanbarcellos.demo.domain.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

}
