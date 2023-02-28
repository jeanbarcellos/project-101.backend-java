package com.jeanbarcellos.project101.domain.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CategoryTest {

    @Test
    void construct_entryName_shouldReturnNewCategory() {
        // Arrange
        String name = "Futebol";

        // Act
        Category category = new Category(name);

        // Assert
        assertEquals(name, category.getName());
    }

    @Test
    void construct_entryIdAndName_shouldReturnNewCategory() {
        // Arrange
        UUID id = UUID.randomUUID();
        String name = "Futebol";

        // Act
        Category category = new Category(id, name);

        // Assert
        assertEquals(id, category.getId());
        assertEquals(name, category.getName());
    }
}
