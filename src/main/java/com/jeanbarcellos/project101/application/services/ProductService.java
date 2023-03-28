package com.jeanbarcellos.project101.application.services;

import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeanbarcellos.core.dto.SuccessResponse;
import com.jeanbarcellos.core.exception.NotFoundException;
import com.jeanbarcellos.core.exception.ValidationException;
import com.jeanbarcellos.core.validation.Validator;
import com.jeanbarcellos.project101.application.dtos.ProductRequest;
import com.jeanbarcellos.project101.application.dtos.ProductResponse;
import com.jeanbarcellos.project101.application.mappers.ProductMapper;
import com.jeanbarcellos.project101.domain.entities.Product;
import com.jeanbarcellos.project101.domain.repositories.CategoryRepository;
import com.jeanbarcellos.project101.domain.repositories.ProductRepository;

@Service
public class ProductService {

    private static final String MSG_ERROR_PRODUCT_NOT_INFORMED = "O ID da categoria deve ser informado.";
    private static final String MSG_ERROR_PRODUCT_NOT_FOUND = "Não há categoria para o ID informado. -> %s";
    private static final String MSG_PRODUCT_ACTIVATED_SUCCESSFULLY = "Produto '%s' ativado com sucesso.";
    private static final String MSG_PRODUCT_INACTIVATED_SUCCESSFULLY = "Produto '%s' desativado com sucesso.";

    @Autowired
    private Validator validator;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductMapper productMapper;

    @PostConstruct
    public void init() {
        this.productMapper.setProviderFindCategoryById(
                categoryId -> this.categoryRepository.findById(categoryId).orElse(null));
    }

    public List<ProductResponse> getAll() {
        return ProductResponse.of(this.productRepository.findAll());
    }

    public List<ProductResponse> getByCategory(UUID categoryId) {
        return ProductResponse.of(this.productRepository.findByCategoryId(categoryId));
    }

    public ProductResponse getById(UUID id) {
        return ProductResponse.of(this.findByIdOrThrow(id));
    }

    public ProductResponse insert(ProductRequest request) {
        this.validator.validate(request);

        var product = this.productMapper.toProduct(request);

        product = this.productRepository.save(product);

        return ProductResponse.of(product);
    }

    public ProductResponse update(ProductRequest request) {
        this.validator.validate(request);

        this.validateExistsById(request.getId());

        var product = this.findByIdOrThrow(request.getId());

        this.productMapper.copyProperties(product, request);

        product = this.productRepository.save(product);

        return ProductResponse.of(product);
    }

    public SuccessResponse activate(UUID id) {
        var product = this.findByIdOrThrow(id);

        product.activate();

        this.productRepository.save(product);

        return SuccessResponse.of(String.format(MSG_PRODUCT_ACTIVATED_SUCCESSFULLY, id));
    }

    public SuccessResponse inactivate(UUID id) {
        var product = this.findByIdOrThrow(id);

        product.inactivate();

        this.productRepository.save(product);

        return SuccessResponse.of(String.format(MSG_PRODUCT_INACTIVATED_SUCCESSFULLY, id));
    }

    private Product findByIdOrThrow(UUID id) {
        return this.productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(MSG_ERROR_PRODUCT_NOT_FOUND, id)));
    }

    private void validateExistsById(UUID id) {
        if (ObjectUtils.isEmpty(id)) {
            throw new ValidationException(MSG_ERROR_PRODUCT_NOT_INFORMED);
        }

        if (!productRepository.existsById(id)) {
            throw new NotFoundException(String.format(MSG_ERROR_PRODUCT_NOT_FOUND, id));
        }
    }
}