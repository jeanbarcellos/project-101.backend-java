package com.jeanbarcellos.project101.application.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.jeanbarcellos.core.dto.SuccessResponse;
import com.jeanbarcellos.core.exception.NotFoundException;
import com.jeanbarcellos.core.exception.ValidationException;
import com.jeanbarcellos.core.validation.Validator;
import com.jeanbarcellos.project101.application.dtos.CategoryRequest;
import com.jeanbarcellos.project101.application.dtos.CategoryResponse;
import com.jeanbarcellos.project101.application.mappers.CategoryMapper;
import com.jeanbarcellos.project101.domain.entities.Category;
import com.jeanbarcellos.project101.domain.repositories.CategoryRepository;

@Service
public class CategoryService {

    private static final String MSG_ERROR_CATEGORY_NOT_INFORMED = "O ID da categoria deve ser informado.";
    private static final String MSG_ERROR_CATEGORY_NOT_FOUND = "Não há categoria para o ID informado. -> %s";
    private static final String MSG_CATEGORY_DELETED_SUCCESSFULLY = "Categoria '%s' excluída com sucesso.";

    @Autowired
    private Validator validator;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    public List<CategoryResponse> getAll() {
        return CategoryResponse.of(this.categoryRepository.findAll());
    }

    public CategoryResponse getById(UUID id) {
        return CategoryResponse.of(this.findByIdOrThrow(id));
    }

    public CategoryResponse insert(CategoryRequest request) {
        this.validator.validate(request);

        var category = this.categoryMapper.toCategory(request);

        category = this.categoryRepository.save(category);

        return CategoryResponse.of(category);
    }

    public CategoryResponse update(CategoryRequest request) {
        this.validator.validate(request);

        this.validateExistsById(request.getId());

        var category = this.findByIdOrThrow(request.getId());

        this.categoryMapper.copyProperties(category, request);

        category = this.categoryRepository.save(category);

        return CategoryResponse.of(category);
    }

    public SuccessResponse delete(UUID id) {
        this.validateExistsById(id);

        this.categoryRepository.deleteById(id);

        return SuccessResponse.of(String.format(MSG_CATEGORY_DELETED_SUCCESSFULLY, id));
    }

    private Category findByIdOrThrow(UUID id) {
        return this.categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(MSG_ERROR_CATEGORY_NOT_FOUND, id)));
    }

    private void validateExistsById(UUID id) {
        if (ObjectUtils.isEmpty(id)) {
            throw new ValidationException(MSG_ERROR_CATEGORY_NOT_INFORMED);
        }

        if (!this.categoryRepository.existsById(id)) {
            throw new NotFoundException(String.format(MSG_ERROR_CATEGORY_NOT_FOUND, id));
        }
    }

}
