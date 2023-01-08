package com.jeanbarcellos.demo.application.mappers;

import org.springframework.stereotype.Component;

import com.jeanbarcellos.demo.application.dtos.CategoryRequest;
import com.jeanbarcellos.demo.domain.entities.Category;

@Component
public class CategoryMapper {

    public Category toCategory(CategoryRequest request) {
        return new Category(request.getName());
    }

    public Category copyProperties(Category category, CategoryRequest request) {
        return category.setName(request.getName());
    }

}
