package com.jeanbarcellos.project101.application.mappers;

import java.util.UUID;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.jeanbarcellos.project101.application.dtos.ProductRequest;
import com.jeanbarcellos.project101.domain.entities.Category;
import com.jeanbarcellos.project101.domain.entities.Product;

import lombok.Setter;

@Component
public class ProductMapper {

    @Setter
    private Function<UUID, Category> providerFindById;

    public Product toProduct(ProductRequest request) {
        return Product.builder()
                .category(this.providerFindById.apply(request.getCategoryId()))
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
                .setCategory(this.providerFindById.apply(request.getCategoryId()))
                .setName(request.getName())
                .setDescription(request.getDescription())
                .setImage(request.getImage())
                .setActive(request.getActive())
                .setValue(request.getValue())
                .setQuantity(request.getQuantity());
    }

}
