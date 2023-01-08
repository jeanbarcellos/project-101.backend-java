package com.jeanbarcellos.demo.application.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.jeanbarcellos.core.dto.SuccessResponse;
import com.jeanbarcellos.core.exception.NotFoundException;
import com.jeanbarcellos.core.exception.ValidationException;
import com.jeanbarcellos.demo.application.dtos.CategoryRequest;
import com.jeanbarcellos.demo.application.dtos.CategoryResponse;
import com.jeanbarcellos.demo.application.mappers.CategoryMapper;
import com.jeanbarcellos.demo.domain.entities.Category;
import com.jeanbarcellos.demo.domain.repositories.CategoryRepository;

@Service
public class CategoryService {

    private static final String MSG_ERROR_CATEGORY_NOT_INFORMED = "O ID da categoria deve ser informado.";
    private static final String MSG_ERROR_CATEGORY_NOT_FOUND = "Não há categoria para o ID informado. -> %s";
    private static final String MSG_CATEGORY_DELETED_SUCCESSFULLY = "Categoria '%s' excluída com sucesso.";

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    public List<CategoryResponse> getAll() {
        List<Category> list = categoryRepository.findAll();

        return CategoryResponse.of(list);
    }

    public CategoryResponse getById(UUID id) {
        Category category = this.findByIdOrThrow(id);

        return CategoryResponse.of(category);
    }

    public CategoryResponse insert(CategoryRequest request) {
        Category category = this.categoryMapper.toCategory(request);

        category = this.categoryRepository.save(category);

        return CategoryResponse.of(category);
    }

    public CategoryResponse update(UUID id, CategoryRequest request) {
        this.validateExistsById(id);

        Category category = this.findByIdOrThrow(id);

        this.categoryMapper.copyProperties(category, request);

        category = this.categoryRepository.save(category);

        return CategoryResponse.of(category);
    }

    public SuccessResponse delete(UUID id) {
        this.validateExistsById(id);

        this.categoryRepository.deleteById(id);

        return SuccessResponse.create(String.format(MSG_CATEGORY_DELETED_SUCCESSFULLY, id));
    }

    private Category findByIdOrThrow(UUID id) {
        return this.categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(MSG_ERROR_CATEGORY_NOT_FOUND, id)));
    }

    private void validateExistsById(UUID id) {
        if (ObjectUtils.isEmpty(id)) {
            throw new ValidationException(MSG_ERROR_CATEGORY_NOT_INFORMED);
        }

        if (!categoryRepository.existsById(id)) {
            throw new NotFoundException(String.format(MSG_ERROR_CATEGORY_NOT_FOUND, id));
        }
    }

}
