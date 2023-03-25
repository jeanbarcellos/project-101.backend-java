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
    private static final String MSG_ERROR_PRODUCT_OUT_OF_STOCK = "Produto '%s' sem estoque";
    private static final String MSG_PRODUCT_ACTIVATED_SUCCESSFULLY = "Produto ativado com sucesso.";
    private static final String MSG_PRODUCT_INACTIVATED_SUCCESSFULLY = "Produto desativado com sucesso.";

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductMapper productMapper;

    @PostConstruct
    public void init() {
        this.productMapper.setProviderFindById(categoryId -> this.categoryRepository.findById(categoryId).orElse(null));
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
        var product = this.productMapper.toProduct(request);

        product = this.productRepository.save(product);

        return ProductResponse.of(product);
    }

    public ProductResponse update(ProductRequest request) {
        this.validateExistsById(request.getId());

        var product = this.findByIdOrThrow(request.getId());

        this.productMapper.copyProperties(product, request);

        product = this.productRepository.save(product);

        return ProductResponse.of(product);
    }

    public SuccessResponse activate(UUID id) {
        Product product = this.findByIdOrThrow(id);

        product.activate();

        this.productRepository.save(product);

        return SuccessResponse.create(MSG_PRODUCT_ACTIVATED_SUCCESSFULLY);
    }

    public SuccessResponse inactivate(UUID id) {
        Product product = this.findByIdOrThrow(id);

        product.inactivate();

        this.productRepository.save(product);

        return SuccessResponse.create(MSG_PRODUCT_INACTIVATED_SUCCESSFULLY);
    }

    public ProductResponse updateStock(UUID id, Integer quantity) {
        this.validateExistsById(id);

        if (quantity < Product.QUANTITY_EMPTY) {
            throw new ValidationException(
                    String.format("O quantidade do estoque não pode ser menor que %s", Product.QUANTITY_EMPTY));
        }

        var product = this.findByIdOrThrow(id);

        product.setQuantity(quantity);

        product = this.productRepository.save(product);

        return ProductResponse.of(product);
    }

    public ProductResponse addStock(UUID id, Integer quantity) {
        this.validateExistsById(id);

        if (quantity < Product.QUANTITY_MIN_ADD) {
            throw new ValidationException(
                    String.format("O quantidade minima de produtos a serem adicionados ao estoque é de %s",
                            Product.QUANTITY_MIN_ADD));
        }

        var product = this.findByIdOrThrow(id);

        product.addQuantity(quantity);

        product = this.productRepository.save(product);

        return ProductResponse.of(product);
    }

    public ProductResponse subStoke(UUID id, Integer quantity) {
        this.validateExistsById(id);

        var product = this.findByIdOrThrow(id);

        if (!product.hasQuantity(quantity)) {
            throw new ValidationException(String.format(MSG_ERROR_PRODUCT_OUT_OF_STOCK, product.getName()));
        }

        product.subQuantity(quantity);

        product = this.productRepository.save(product);

        return ProductResponse.of(product);
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