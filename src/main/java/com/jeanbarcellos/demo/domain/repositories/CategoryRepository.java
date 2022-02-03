package com.jeanbarcellos.demo.domain.repositories;

import com.jeanbarcellos.demo.domain.entities.Category;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
