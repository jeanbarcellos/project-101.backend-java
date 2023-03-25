package com.jeanbarcellos.project101.application.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.jeanbarcellos.core.util.CollectionUtils;
import com.jeanbarcellos.project101.domain.entities.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ProductResponse {

    private UUID id;
    private UUID categoryId;
    private String name;
    private String description;
    private String image;
    private Boolean active;

    private BigDecimal value;
    private Integer quantity;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ProductResponse of(Product entity) {
        return ProductResponse.builder()
                .id(entity.getId())
                .categoryId(entity.getCategory().getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .image(entity.getImage())
                .active(entity.getActive())
                .value(entity.getValue())
                .quantity(entity.getQuantity())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static List<ProductResponse> of(List<Product> categories) {
        return CollectionUtils.mapToList(categories, ProductResponse::of);
    }

}
