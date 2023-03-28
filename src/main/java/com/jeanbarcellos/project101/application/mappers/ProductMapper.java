package com.jeanbarcellos.project101.application.mappers;

import java.util.UUID;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.jeanbarcellos.project101.application.dtos.ProductRequest;
import com.jeanbarcellos.project101.domain.entities.Category;
import com.jeanbarcellos.project101.domain.entities.Product;

import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true)
@Component
public class ProductMapper {

    private Function<UUID, Category> providerFindCategoryById;

    public Product toProduct(ProductRequest request) {
        return Product.builder()
                .category(this.providerFindCategoryById.apply(request.getCategoryId()))
                .name(request.getName())
                .description(request.getDescription())
                .image(request.getImage())
                .active(request.getActive())
                .value(request.getValue())
                .quantity(request.getQuantity())
                .build();
    }

    public Product copyProperties(Product product, ProductRequest request) {
        return product
                .setCategory(this.providerFindCategoryById.apply(request.getCategoryId()))
                .setName(request.getName())
                .setDescription(request.getDescription())
                .setImage(request.getImage())
                .setActive(request.getActive())
                .setValue(request.getValue())
                .setQuantity(request.getQuantity());
    }

}
