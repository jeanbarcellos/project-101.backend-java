package com.jeanbarcellos.demo.application.services;

import static org.springframework.util.ObjectUtils.isEmpty;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.jeanbarcellos.core.dtos.SuccessResponse;
import com.jeanbarcellos.core.exceptions.NotFoundException;
import com.jeanbarcellos.core.exceptions.ValidationException;
import com.jeanbarcellos.demo.application.dtos.CategoryRequest;
import com.jeanbarcellos.demo.application.dtos.CategoryResponse;
import com.jeanbarcellos.demo.application.mappers.CategoryMapper;
import com.jeanbarcellos.demo.domain.entities.Category;
import com.jeanbarcellos.demo.domain.repositories.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private static final String MSG_ERROR_CATEGORY_NOT_INFORMED = "O ID da categoria deve ser informado.";
    private static final String MSG_ERROR_CATEGORY_NOT_FOUND = "Não há categoria para o ID informado.";
    private static final String MSG_CATEGORY_DELETED_SUCCESSFULLY = "A categoria excluída com sucesso.";

    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryResponse> getAll() {
        List<Category> list = categoryRepository.findAll();

        return list.stream().map(CategoryResponse::from).collect(Collectors.toList());
    }

    public CategoryResponse getById(UUID id) {
        Category category = this.getCategory(id);

        return CategoryMapper.toResponse(category);
    }

    public CategoryResponse insert(CategoryRequest request) {
        Category category = CategoryMapper.toCategory(request);

        category = categoryRepository.save(category);

        return CategoryMapper.toResponse(category);
    }

    public CategoryResponse update(UUID id, CategoryRequest request) {
        this.validateExistsById(id);

        Category category = this.getCategory(id);

        CategoryMapper.updateFromRequest(category, request);

        category = categoryRepository.save(category);

        return CategoryMapper.toResponse(category);
    }

    public SuccessResponse delete(UUID id) {
        this.validateExistsById(id);

        categoryRepository.deleteById(id);

        return SuccessResponse.create(MSG_CATEGORY_DELETED_SUCCESSFULLY);
    }

    private Category getCategory(UUID id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(MSG_ERROR_CATEGORY_NOT_FOUND));
    }

    private void validateExistsById(UUID id) {
        if (isEmpty(id)) {
            throw new ValidationException(MSG_ERROR_CATEGORY_NOT_INFORMED);
        }

        if (!categoryRepository.existsById(id)) {
            throw new NotFoundException(MSG_ERROR_CATEGORY_NOT_FOUND);
        }
    }

}
