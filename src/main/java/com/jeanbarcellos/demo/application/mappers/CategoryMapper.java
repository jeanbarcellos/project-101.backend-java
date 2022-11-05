package com.jeanbarcellos.demo.application.mappers;

import com.jeanbarcellos.demo.application.dtos.CategoryRequest;
import com.jeanbarcellos.demo.application.dtos.CategoryResponse;
import com.jeanbarcellos.demo.domain.entities.Category;

public class CategoryMapper {

    private CategoryMapper() {
    }

    public static Category toCategory(CategoryRequest request) {
        return new Category(request.getName());
    }

    public static Category copyProperties(Category category, CategoryRequest request) {
        return category.setName(request.getName());
    }

    public static CategoryResponse toCategoryResponse(Category category) {
        return CategoryResponse.of(category);
    }

}
