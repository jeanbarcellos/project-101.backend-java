package com.jeanbarcellos.demo.app.application.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

import com.jeanbarcellos.demo.app.domain.entities.Category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {

    public UUID id;

    public String name;

    public LocalDateTime createdAt;

    public LocalDateTime updatedAt;

    public static CategoryResponse from(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();

    }

}
