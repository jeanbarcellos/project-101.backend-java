package com.jeanbarcellos.demo.domain.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CategoryTest {

    @Test
    public void construct_entryName_shouldReturnNewCategory() {
        // Arrange
        String name = "Futebol";

        // Act
        Category category = new Category(name);

        // Assert
        assertEquals(name, category.getName());
    }

    @Test
    public void construct_entryIdAndName_shouldReturnNewCategory() {
        // Arrange
        Integer id = 999;
        String name = "Futebol";

        // Act
        Category category = new Category(id, name);

        // Assert
        assertEquals(id, category.getId());
        assertEquals(name, category.getName());
    }
}
