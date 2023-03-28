package com.jeanbarcellos.project101.domain.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jeanbarcellos.project101.domain.entities.Product;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    public List<Product> findByCategoryId(UUID categoryId);
}
