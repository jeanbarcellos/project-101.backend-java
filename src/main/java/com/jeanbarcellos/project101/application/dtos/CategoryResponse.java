package com.jeanbarcellos.project101.application.dtos;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.jeanbarcellos.core.util.CollectionUtils;
import com.jeanbarcellos.project101.domain.entities.Category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {

    private UUID id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CategoryResponse of(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();
    }

    public static List<CategoryResponse> of(List<Category> categories) {
        return CollectionUtils.mapToList(categories, CategoryResponse::of);
    }

}
